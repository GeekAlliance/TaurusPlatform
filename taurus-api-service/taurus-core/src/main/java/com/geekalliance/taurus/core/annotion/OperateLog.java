package com.geekalliance.taurus.core.annotion;

import com.geekalliance.taurus.core.enums.OperateTypeEnum;

import java.lang.annotation.*;

/**
 * 操作日志
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-11 21:28
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OperateLog {

    /**
     * @return 业务名称
     */
    String BusinessName() default "";

    OperateTypeEnum operateType() default OperateTypeEnum.OTHER;
}
