package com.geekalliance.taurus.core.validation;

/**
 * 长度约束
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-17 21:44
 */
public interface LengthConstraint {
    /**
     * ID 长度
     */
    int ID_LENGTH=32;

    /**
     * code length
     */
    int CODE_LENGTH=32;

    /**
     * name length
     */
    int NAME_LENGTH=64;
}
