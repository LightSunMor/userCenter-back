package com.usercenterdemo.exception;

import com.usercenterdemo.common.BaseResponse;
import com.usercenterdemo.common.ErrorCode;
import com.usercenterdemo.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 *  全局异常处理器
 *      集中处理异常，返回对象，打印error日志
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 接手捕获BusinessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e)
    {
        log.error("businessException"+e.getDescription(),e); //输出异常类型，和异常信息
        return ResultUtils.error(ErrorCode.PARAMS_ERROR,e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e)
    {
        log.error("runtimeException ",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }
}
