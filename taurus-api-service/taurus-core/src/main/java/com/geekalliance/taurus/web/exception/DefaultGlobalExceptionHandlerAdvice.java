package com.geekalliance.taurus.web.exception;

import com.geekalliance.taurus.core.exception.BaseException;
import com.geekalliance.taurus.core.exception.SystemErrorType;
import com.geekalliance.taurus.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

/**
 * @author maxuqiang
 */
@Slf4j
public class DefaultGlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {MultipartException.class})
    public Result uploadFileLimitException(MultipartException ex) {
        log.error("upload file error", ex);
        if (ex.getCause().getCause() instanceof MaxUploadSizeExceededException) {
            return Result.fail(SystemErrorType.UPLOAD_FILE_SIZE_LIMIT);
        } else {
            return Result.fail(SystemErrorType.MULTIPART_ERROR);
        }
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result argumentNotValidException(MethodArgumentNotValidException ex) {
        String errorValue = ex.getBindingResult().getFieldError().getDefaultMessage();
        return Result.fail(SystemErrorType.ARGUMENT_NOT_VALID).setMsg(errorValue);
    }

    @ExceptionHandler(value = {BindException.class})
    public Result paramNotValidException(BindException ex) {
        return Result.fail(SystemErrorType.PARAM_TYPE_ERROR);
    }

    @ExceptionHandler(value = {HttpMessageConversionException.class})
    public Result conversionException(HttpMessageConversionException e) {
        return Result.fail(SystemErrorType.PARAMETER_CONVERSION_ERROR);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public Result paramNotValidException(HttpMessageNotReadableException ex) {
        return Result.fail(SystemErrorType.PARAM_TYPE_ERROR);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public Result exception(HttpRequestMethodNotSupportedException e) {
        return Result.fail(SystemErrorType.UNSUPPORTED_REQUEST_METHOD);
    }

    @ExceptionHandler(value = {BaseException.class})
    public Result baseException(BaseException ex) {
        return Result.fail(ex.getErrorType());
    }

    @ExceptionHandler(value = {Exception.class})
    public Result exception(Exception e) {
        log.error("default global exception handler advice exception", e);
        return Result.fail(SystemErrorType.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = {Throwable.class})
    public Result throwable(Throwable e) {
        log.error("default global exception handler advice throwable", e);
        return Result.fail(SystemErrorType.SYSTEM_ERROR);
    }
}
