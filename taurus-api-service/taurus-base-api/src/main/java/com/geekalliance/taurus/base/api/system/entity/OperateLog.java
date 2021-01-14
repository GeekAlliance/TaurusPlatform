package com.geekalliance.taurus.base.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 操作日志表
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_operate_log")
public class OperateLog extends BaseEntity {
    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 操作类型
     */
    private Integer operateType;

    /**
     * 操作系统
     */
    private Integer operateSystem;

    /**
     * 访问地址
     */
    private Integer ipAddress;

    /**
     * 浏览器
     */
    private Integer browser;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求方式
     */
    private Integer requestMethod;

    /**
     * 类名称
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 返回结果
     */
    private String returnResult;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作账号
     */
    private String account;

    /**
     * 执行结果
     */
    private String successFlag;

    /**
     * 操作用时
     */
    private Integer responseTime;
}