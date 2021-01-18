package com.geekalliance.taurus.core.exception;


import com.geekalliance.taurus.core.i18n.LocaleMessage;
import com.geekalliance.taurus.core.utils.SpringContextUtil;

import java.io.Serializable;

/**
 * @author maxuqiang
 */
@SuppressWarnings("PMD")
public interface ErrorType extends Serializable {
    LocaleMessage localeMessage = (LocaleMessage) SpringContextUtil.getBean("localeMessage");

    /**
     * 返回code
     *
     * @return
     */
    int getCode();

    /**
     * 返回msg
     *
     * @return
     */
    String getMessage();
}
