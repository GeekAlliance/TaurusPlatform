package com.geekalliance.taurus.core.annotion;

import java.lang.annotation.*;

/**
 * 业务名称
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-11 21:39
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BusinessName {

    String name() default "";
}
