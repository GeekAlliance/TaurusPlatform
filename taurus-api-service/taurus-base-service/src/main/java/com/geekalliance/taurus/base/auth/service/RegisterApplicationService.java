package com.geekalliance.taurus.base.auth.service;

import com.geekalliance.taurus.base.api.auth.dto.RegisterApplicationDTO;
import com.geekalliance.taurus.base.api.auth.entity.Application;
import com.geekalliance.taurus.base.api.auth.entity.Resource;
import com.geekalliance.taurus.base.api.auth.enums.ResourceTypeEnum;
import com.geekalliance.taurus.base.api.auth.register.entity.RegisterAction;
import com.geekalliance.taurus.base.api.auth.register.entity.RegisterApplication;
import com.geekalliance.taurus.base.api.auth.register.entity.RegisterModule;
import com.geekalliance.taurus.base.auth.mapper.ApplicationMapper;
import com.geekalliance.taurus.core.entity.BaseEntity;
import com.geekalliance.taurus.core.enums.TreeEnum;
import com.geekalliance.taurus.core.exception.InternalException;
import com.geekalliance.taurus.rdb.service.RdbService;
import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.toolkit.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class RegisterApplicationService extends RdbService<ApplicationMapper, Application> {
    @Autowired
    private ResourceService resourceService;

    @Transactional(rollbackFor = Exception.class)
    public boolean register(List<RegisterApplication> applications, Boolean addApp) throws InternalException {
        RegisterApplicationDTO regApp = new RegisterApplicationDTO();
        regApp.setApplications(new ArrayList<>());
        regApp.setResources(new ArrayList<>());
        for (RegisterApplication application : applications) {
            if (Objects.isNull(application.getModuleStartSortIndex())) {
                application.setModuleStartSortIndex(999999);
            }
            Application app = beanGenerator.convert(application, Application.class);
            app.setDeleteFlag(logicNotDeleteValue);
            Application appDb = getById(app.getId());
            // 是否添加应用，不添加应用的话应用必须先添加好才能注册。对于HTTP接口注册必须先添加应用，内部微服务注册不需要添加应用。
            if (!addApp) {
                if (Objects.isNull(appDb)) {
                    continue;
                } else {
                    app = appDb;
                }
            } else {
                if (!Objects.isNull(appDb)) {
                    app = appDb;
                }
                setBaseEntity(app, StringPool.NONE);
            }
            log.info("注册应用 ID:{} 名称:{}", app.getId(), app.getName());
            regApp.getApplications().add(app);
            regApp.setUserId(app.getCreateBy());
            addRegisterModules(regApp, application, Optional.ofNullable(application.getModules()));
        }
        this.registerAllList(regApp);
        return true;
    }


    private void registerAllList(RegisterApplicationDTO regApp) {
        if (CollectionUtils.isNotEmpty(regApp.getApplications())) {
            saveOrUpdateBatch(regApp.getApplications());
        }
        if (CollectionUtils.isNotEmpty(regApp.getResources())) {
            resourceService.saveOrUpdateBatch(regApp.getResources());
        }
    }


    private void addRegisterModules(RegisterApplicationDTO regApp, RegisterApplication application, Optional<List<RegisterModule>> modules) {
        if (modules.isPresent()) {
            for (RegisterModule module : modules.get()) {
                module.setParentId(TreeEnum.ROOT_NODE.getCode());
                module.setApplication(application.getId());
                module.setLevel(TreeEnum.ROOT_LEVEL.getCode());
                addRegisterModuleAndSubModule(regApp, application, module);
            }
        }
    }


    private void addRegisterModuleAndSubModule(RegisterApplicationDTO regApp, RegisterApplication application, RegisterModule module) {
        Resource moduleResource = beanGenerator.convert(module, Resource.class);
        moduleResource.setType(ResourceTypeEnum.MODULE.getCode());
        moduleResource.setDeleteFlag(logicNotDeleteValue);
        regApp.getResources().add(moduleResource);
        List<RegisterAction> actions = Optional.ofNullable(module.getActions()).orElseGet(Collections::emptyList);
        for (RegisterAction action : actions) {
            Resource actionResource = beanGenerator.convert(action, Resource.class);
            actionResource.setDeleteFlag(logicNotDeleteValue);
            actionResource.setType(ResourceTypeEnum.ACTION.getCode());
            actionResource.setApplication(application.getId());
            actionResource.setParent(moduleResource.getId());
            setBaseEntity(actionResource, regApp.getUserId());
            regApp.getResources().add(actionResource);
        }

        List<RegisterModule> subModules = Optional.ofNullable(module.getSubModule()).orElse(Collections.emptyList());
        for (RegisterModule subModule : subModules) {
            int level = Integer.parseInt(module.getLevel()) + 1;
            subModule.setLevel(String.valueOf(level));
            subModule.setParentId(moduleResource.getId());
            subModule.setApplication(application.getId());
            addRegisterModuleAndSubModule(regApp, application, subModule);
        }
    }


    private void setBaseEntity(BaseEntity entity, String userId) {
        entity.setUpdateBy(userId);
        entity.setCreateBy(userId);
        entity.setCreateAt(new Date());
        entity.setUpdateAt(new Date());
    }
}
