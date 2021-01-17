package com.geekalliance.taurus.rdb.service.liquibase;


import com.geekalliance.taurus.rdb.config.DynamicDataSourceConfig;
import com.geekalliance.taurus.rdb.utils.ExclusiveLockUtils;
import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.lockservice.DatabaseChangeLogLock;
import liquibase.lockservice.LockService;
import liquibase.servicelocator.LiquibaseService;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateDatabaseChangeLogLockTableStatement;
import liquibase.statement.core.DropTableStatement;
import liquibase.statement.core.InitializeDatabaseChangeLogLockTableStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Table;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author maxuqiang
 */
@Slf4j
@LiquibaseService
public class LiquibaseLockService implements LockService {
    protected Database database;
    protected boolean hasChangeLogLock;
    private Long changeLogLockPollRate;
    private Long changeLogLockRecheckTime;
    private boolean isDatabaseChangeLogLockTableInitialized;
    private Boolean hasDatabaseChangeLogLockTable;
    private Connection connection;

    public LiquibaseLockService() {
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean supports(Database database) {
        return true;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public void setChangeLogLockWaitTime(long changeLogLockWaitTime) {
        this.changeLogLockPollRate = changeLogLockWaitTime;
    }

    @Override
    public void setChangeLogLockRecheckTime(long changeLogLockRecheckTime) {
        this.changeLogLockRecheckTime = changeLogLockRecheckTime;
    }

    @Override
    public void init() throws DatabaseException {
        boolean createdTable = false;
        Executor executor = ExecutorService.getInstance().getExecutor(this.database);
        if (!this.hasDatabaseChangeLogLockTable()) {
            try {
                executor.comment("Create Database Lock Table");
                executor.execute(new CreateDatabaseChangeLogLockTableStatement());
                this.database.commit();
                log.info("created database lock table with name: {}", this.database.escapeTableName(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName(), this.database.getDatabaseChangeLogLockTableName()));
            } catch (DatabaseException var5) {
                if (var5.getMessage() == null || !var5.getMessage().contains("exists")) {
                    throw var5;
                }
                log.error("database lock table already appears to exist due to exception: {} . continuing on", var5.getMessage());
            }
            this.hasDatabaseChangeLogLockTable = true;
            createdTable = true;
            this.hasDatabaseChangeLogLockTable = true;
        }

        if (!this.isDatabaseChangeLogLockTableInitialized(createdTable)) {
            executor.comment("Initialize Database Lock Table");
            executor.execute(new InitializeDatabaseChangeLogLockTableStatement());
            this.database.commit();
            this.isDatabaseChangeLogLockTableInitialized = true;
        }

        if (executor.updatesDatabase() && this.database instanceof DerbyDatabase && ((DerbyDatabase) this.database).supportsBooleanDataType() || this.database.getClass().isAssignableFrom(DB2Database.class) && ((DB2Database) this.database).supportsBooleanDataType()) {
            String lockTable = this.database.escapeTableName(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName(), this.database.getDatabaseChangeLogLockTableName());
            Object obj = executor.queryForObject(new RawSqlStatement("SELECT MIN(locked) AS test FROM " + lockTable + " FETCH FIRST ROW ONLY"), Object.class);
            if (!(obj instanceof Boolean)) {
                executor.execute(new DropTableStatement(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName(), this.database.getDatabaseChangeLogLockTableName(), false));
                executor.execute(new CreateDatabaseChangeLogLockTableStatement());
            }
        }
    }

    @Override
    public boolean hasChangeLogLock() {
        return this.hasChangeLogLock;
    }

    @Override
    public void waitForLock() throws LockException {
        this.acquireLock();
    }

    @Override
    public boolean acquireLock() throws LockException {
        try {
            this.database.rollback();
            this.init();
            this.database.commit();
        } catch (Exception var15) {
            throw new LockException(var15);
        } finally {
            try {
                this.database.rollback();
            } catch (DatabaseException var14) {
            }
        }
        // 机器性能慢的情况下会获取锁超时 添加重试机制
        int lockErrorCount = 0;
        int retryTimes = 10;
        boolean lockSuccess = false;
        Exception lockException = null;
        while (!lockSuccess && lockErrorCount < retryTimes) {
            try {
                setConnection();
                log.info("exclusive lock {} times {}", this.database.getDatabaseChangeLogLockTableName(), lockErrorCount + 1);
                ExclusiveLockUtils.lockTable(connection, this.database.getDatabaseChangeLogLockTableName(), "LOCKED");
                lockSuccess = true;
            } catch (SQLException e) {
                lockSuccess = false;
                lockErrorCount++;
                lockException = e;
            }
        }
        if (!lockSuccess && Objects.nonNull(lockException)) {
            throw new LockException("retry " + retryTimes + " times lock error", lockException);
        }
        return lockSuccess;
    }

    @Override
    public void releaseLock() throws LockException {
        if (Objects.isNull(connection)) {
            throw new LockException("dynamic datasource config connection is null");
        }
        try {
            ExclusiveLockUtils.releaseLock(connection);
        } catch (SQLException e) {
            log.info("release lock sql exception {}", e.getErrorCode());
        }
    }

    @Override
    public DatabaseChangeLogLock[] listLocks() throws LockException {
        List<DatabaseChangeLogLock> allLocks = new ArrayList();
        return allLocks.toArray(new DatabaseChangeLogLock[allLocks.size()]);
    }

    @Override
    public void forceReleaseLock() throws LockException, DatabaseException {
        this.init();
        this.releaseLock();
    }

    @Override
    public void reset() {
        this.hasChangeLogLock = false;
        this.hasDatabaseChangeLogLockTable = null;
    }

    @Override
    public void destroy() throws DatabaseException {
        try {
            DatabaseObject example = (new Table()).setName(this.database.getDatabaseChangeLogLockTableName()).setSchema(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName());
            if (SnapshotGeneratorFactory.getInstance().has(example, this.database)) {
                DatabaseObject table = SnapshotGeneratorFactory.getInstance().createSnapshot(example, this.database);
                DiffOutputControl diffOutputControl = new DiffOutputControl(true, true, false, null);
                Change[] change = ChangeGeneratorFactory.getInstance().fixUnexpected(table, diffOutputControl, this.database, this.database);
                SqlStatement[] sqlStatement = change[0].generateStatements(this.database);
                ExecutorService.getInstance().getExecutor(this.database).execute(sqlStatement[0]);
            }

            this.reset();
        } catch (InvalidExampleException var6) {
            throw new UnexpectedLiquibaseException(var6);
        }
    }


    private boolean hasDatabaseChangeLogLockTable() throws DatabaseException {
        if (this.hasDatabaseChangeLogLockTable == null) {
            try {
                this.hasDatabaseChangeLogLockTable = SnapshotGeneratorFactory.getInstance().hasDatabaseChangeLogLockTable(this.database);
            } catch (LiquibaseException var2) {
                throw new UnexpectedLiquibaseException(var2);
            }
        }

        return this.hasDatabaseChangeLogLockTable;
    }

    private boolean isDatabaseChangeLogLockTableInitialized(boolean tableJustCreated) throws DatabaseException {
        if (!this.isDatabaseChangeLogLockTableInitialized) {
            Executor executor = ExecutorService.getInstance().getExecutor(this.database);

            try {
                this.isDatabaseChangeLogLockTableInitialized = executor.queryForInt(new RawSqlStatement("SELECT COUNT(*) FROM " + this.database.escapeTableName(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName(), this.database.getDatabaseChangeLogLockTableName()))) > 0;
            } catch (LiquibaseException var4) {
                if (executor.updatesDatabase()) {
                    throw new UnexpectedLiquibaseException(var4);
                }
                this.isDatabaseChangeLogLockTableInitialized = !tableJustCreated;
            }
        }
        return this.isDatabaseChangeLogLockTableInitialized;
    }

    private void setConnection() throws LockException {
        try {
            if (Objects.isNull(connection) || connection.isClosed()) {
                connection = DynamicDataSourceConfig.getConnection();
            }
        } catch (SQLException e) {
            throw new LockException("dynamic datasource config connection sql exception");
        }
        if (Objects.isNull(connection)) {
            throw new LockException("dynamic datasource config connection is null");
        }
    }
}
