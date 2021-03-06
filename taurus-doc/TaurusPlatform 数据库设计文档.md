# TaurusPlatform 数据库设计文档

## 1. 文档介绍

### 1.1 编写目的

作为软件设计文档的重要组成部分，本文档主要对该软件后台数据库设计做出统一规定，他是开发人员比那吗和测试人员测试的重要参考依据和指导标准。

### 1.2 适应范围

本概要设计文档提供给数据库设计人员、系统开发人员、测试人员及项目组组内成员，不提供给本组织以外的的人员，版权归本组织所有。

## 2. 环境说明

本系统数据库设计指定数据库设计师专人统一建模及后期数据库维护，数据库脚本使用liqubase框架统一管理，管理内容包括：数据域管理、通用字段管理、结构管理、脚本生成以及后期版本升级，通过数据域建立不同dbms兼容管理，保证系统支持多数据源及不同dbms数据源。主要支持的数据库包括：oracle、mysql、sql server和postgre。

## 3. 数据库设计规范

**规范--遵守行业规范**
如果项目有相关国家/行业强制性数据结构标准规范存在时，用于存储某业务数据的业务表在表名命名上原则上应该遵从标准规定，其表中相关字段的中文名称（即数据项名称）若标准规范上有规定的应遵循规定。

**规范--全部小写原则**
所有数据库对象命名字母全部小写。

**规范--字符范围原则**
只能使用英文字母和下划线进行命名。

**规范--分段命名原则**
命名中多个单词间采用下划线(_)分隔，以便阅读同时方便某些工具对数据库对象的映射。

**规范--勿用保留字**
数据库对象命名禁止直接使用数据库保留关键字，但分段中可以使用。

**规范--统一性原则**
建立数据库字典，管理命名中使用的前缀、英文单词、英文单词缩写等。对于同一含义尽量使用相同的单词命名，以免引起误解。

**规范--简单命名原则**
数据库对象命名总长度不超过30个字符。

**建议--富有含义原则**
数据库对象命名通常用能表示其内容或者含义的英文单词或其缩写表示。

## 4. 数据库对象前缀规范

| 对象     | 全称                   | 前缀规范                             |
| -------- | ---------------------- | ------------------------------------ |
| 表       | table                  | *cf_*、*sm_*、*pm_*等,根据模块自定义 |
| 视图     | view                   | vw_                                  |
| 索引     | index                  | ix_                                  |
| 存储过程 | stored procedure       | sp_                                  |
| 函数     | function               | fc_                                  |
| 序列     | sequence               | sq_                                  |
| 触发器   | trigger                | tr_                                  |
| 主键约束 | primary key constraint | pk_                                  |
| 外键约束 | foreign key constraint | fk_                                  |
| 默认约束 | default constraint     | df_                                  |
| 唯一约束 | unique constraint      | uq_                                  |
| 检查约束 | check constraint       | ck_                                  |

*说明：上表中未提到的对象命名中不使用前缀，后期如果需要在添加。*

## 5.数据库对象命名规范

- **表名使用单数名：** *例如：对存储客人信息的表（customer）不能使用customers。*
- **避免无谓的表格后缀：** *表是用来存储数据信息的,表是行的集合。那么如果表名已经能够很好地说明其包含的数据信息，就不需要再添加体现上面两点的后缀了； guest_info(存储客户信息)应写成guest，flight_list（存储航班信息的表）应写成flight。*
- **所有表示数目的字段，都应该以count作为结尾**；
- **所有代表链接的字段，均为url结尾**；
- 所有名称的字符范围为：**a-z 和_(下划线)。**不允许使用其他字符作为名称；
- 采用英文**单词或英文短语（包括缩写）作为名称**，不能使用无意义的字符或汉语拼音；
- **名称应该清晰明了**，能够准确表达事物的含义，最好可读，遵循“见名知意”的原则；
- **名称长度不能超过30个字节。**

### 5.1 数据库命名规范（database）

- 规范：数据库名称不需要简写，根据实际项目来命名。

### 5.2 表（table）

- **规范**：定义项目模块缩写为前缀开头，未定义则不使用前缀；
- **规范**：表名采用多段式命名，各单词间用下划线分隔；
- **规范**：表名只允许用英文字母、下划线进行命名，不允许用中文或者其他符号；
- **规范**：表名全部字母大写；
- **规范**：根据历史习惯各系统常用表类前缀作如下约定

| 模块名称 | 说明                 | 前缀 | 示例                           |
| -------- | -------------------- | ---- | ------------------------------ |
| 权限管理 | authority management | am_  |                                |
| 系统配置 | system configuration | sc_  |                                |
| 版本控制 | database version     | dv_  | 如：dv_change_log （变更日志） |
| 生产监视 | monitoring system    | ms_  |                                |
| 基础数据 | basic data           | bd_  |                                |

- **建议：**表名也用于相关索引、约束、主键等命名，因此为了避免相关对象命名长度超过限制，**建议表名长度不要超过20。**
- **建议：**表的命名方式建议采用moudle_entity方式。moudle表示数据库对象所属的系统、模块名或者主题分类的缩写。entity表示目的表代表的实体名称。
- **建议：**命名时应尽可能地使名称能够清晰准确表达对象的内容，使用能代表其含义的英文单词、英文单词缩写。

### 5.3 列/字段（column）

- **规范**：列名无需使用前缀；
- **规范**：列名只允许使用大写英文字母、下划线、数字进行命名；尽量只使用大写英文字母和下划线；
- **规范**：列名采用多段式命名时，各单词间用下划线分隔；
- **规范**：列名不能直接使用数据库保留字；
- ***建议***：列的命名应尽可能地采用简洁明了的列名以准确描述列的内容含义, 根据需要可以一个单词或者多个单词进行命名；
- ***建议***：日期类型字段推荐以“_date”结尾的名字命名，时间类型的字段推荐以“_time”结尾的名字命名。
- ***建议***：主键列命名为“id”或者以 “_id”为后缀进行命名。对于需要在其他表中引用的主键字段以“_id”后缀方式命名，普通表主键无需加后缀。如基础信息表的主键一般应命名为“entity_id”方式，而通常业务数据明细表的主键则直接命名为“id”。

示例：

```
1. 正确命名：user_name、audit_time、audit_user；
2. 错误命名：username、username、c_user_name、人员姓名，违反规范；
3. 错误命名：comment、audit，违反保留字。
```

## 6. 模块说明

| 模块名称 | 逻辑名称     | 模块前缀 |
| -------- | ------------ | -------- |
| 基础平台 | base_platform | base_    |
|          |              |          |
|          |              |          |

### 6.1基础平台