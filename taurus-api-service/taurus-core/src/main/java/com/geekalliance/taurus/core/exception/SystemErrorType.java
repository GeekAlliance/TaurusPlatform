package com.geekalliance.taurus.core.exception;

import lombok.Getter;

/**
 * @author maxuqiang
 */

@Getter
public enum SystemErrorType implements ErrorType {
    /**
     * 系统、授权相关全局错误码
     */
    SYSTEM_ERROR(10000000, "system.error"),
    SYSTEM_BUSY(10000001, "system.busy"),
    SYSTEM_NO_HANDLER_FOUND(10000003, "system.no.handler.found"),

    ARGUMENT_NOT_VALID(10000101, "argument.not.valid"),
    UPLOAD_FILE_SIZE_LIMIT(10000102, "upload.file.size.limit"),
    MULTIPART_CONTENT_EMPTY(10000102, "multipart.content.empty"),
    MULTIPART_ERROR(10000103, "multipart.error"),
    RESOURCE_NOT_FOUNT(10000104, "resource.not.fount"),
    NAME_EXIST(10000105, "name.exist"),
    PARAM_TYPE_ERROR(10000107, "param.type.error"),
    PARAMETER_CONVERSION_ERROR(10000109, "parameter.conversion.error"),

    GATEWAY_NOT_FOUND_SERVICE(10010001, "gateway.not.found.service"),
    GATEWAY_ERROR(10010002, "gateway.error"),
    GATEWAY_CONNECT_TIME_OUT(10010003, "gateway.connect.time.out"),

    INVALID_REQUEST(10020001, "invalid.request"),
    INVALID_CLIENT(10020002, "invalid.client"),
    INVALID_GRANT(10020003, "invalid.grant"),
    INVALID_SCOPE(10020004, "invalid.scope"),
    INVALID_TOKEN(10020005, "invalid.token"),
    INSUFFICIENT_SCOPE(10020006, "insufficient.scope"),
    REDIRECT_URI_MISMATCH(10020007, "redirect.uri.mismatch"),
    ACCESS_DENIED(10020008, "access.denied"),
    UNSUPPORTED_REQUEST_METHOD(10020009, "unsupported.request.method"),
    SERVER_ERROR(10020010, "server.error"),
    UNAUTHORIZED_CLIENT(10020011, "unauthorized.client"),
    UNAUTHORIZED(10020012, "unauthorized"),
    UNSUPPORTED_RESPONSE_TYPE(10020013, "unsupported.response.type"),
    UNSUPPORTED_GRANT_TYPE(10020014, "unsupported.grant.type"),
    BAD_CREDENTIALS(10020015, "bad.credentials"),
    EXPIRED_TOKEN(10020016, "expired.token"),

    ;

    /**
     * 错误类型码
     */
    private final int code;
    /**
     * 错误类型描述信息
     */
    private final String msg;

    SystemErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return localeMessage.getMessage(msg);
    }
}
