package com.geekalliance.taurus.core.entity;

import java.io.Serializable;

/**
 * 分组验证
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-13 21:34
 */
public class ValidatedGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分组校验：增加
     */
    public @interface add {
    }

    /**
     * 分组校验：删除
     */
    public @interface delete {
    }

    /**
     * 分组校验：编辑
     */
    public @interface update {
    }

    /**
     * 分组校验：分组
     */
    public @interface group{}

}
