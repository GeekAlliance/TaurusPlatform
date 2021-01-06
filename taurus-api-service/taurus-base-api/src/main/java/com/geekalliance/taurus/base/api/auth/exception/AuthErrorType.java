package com.geekalliance.taurus.base.api.auth.exception;


import com.geekalliance.taurus.core.exception.ErrorType;
import lombok.Getter;
/**
 * @description
 * @date 2019/12/30
 * @author maxuqiang
 **/
@Getter
public enum AuthErrorType implements ErrorType {
    USERNAME_NOT_FOUND(30010001,"username.not.found"),
    USER_NOT_ROLE(30010002,"user.not.role"),
    CLIENT_NOT_FOUND(30010003,"client.not.found"),

    APPLICATION_EXIST(30020001,"application.is.exist"),
    APPLICATION_NO_EXIST(30020002,"application.no.exist"),

    USER_EXIST(30030001,"user.have.existed"),
    USER_OLD_PASSWORD_MISMATCH(30030002,"old.password.error"),
    USERNAME_EMPTY(30030003,"username.empty"),
    USER_PASSWORD_SAME(30030004,"same.password"),
    USER_NOT_EXIST(30030005,"user.not.existed"),

    ;

    /**
     * 错误类型码
     */
    private int code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    AuthErrorType(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return localeMessage.getMessage(msg);
    }
}
