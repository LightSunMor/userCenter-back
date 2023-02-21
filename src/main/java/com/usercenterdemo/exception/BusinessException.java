package com.usercenterdemo.exception;

import com.usercenterdemo.common.ErrorCode;

/**
 *  自定义异常
 *      给原本的运行时异常，多定义两个字段code description （满足当前业务需求）
 *
 * @author MorSun
 */
public class BusinessException extends RuntimeException{

    private int code;

    private String description;

    /**
     *
     * @param code
     * @param description
     * @param message
     */
    public BusinessException(int code, String description,String message) {
        super(message);
        this.code = code;
        this.description = description;
    }

    /**
     *
     * @param errorCode
     */
    public BusinessException(ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.code=errorCode.getCode();
        this.description=errorCode.getDescription();
    }

    /**
     *  因为错误枚举类，没有定义description
     * @param errorCode
     * @param description
     */
    public BusinessException(ErrorCode errorCode,String description)
    {
        super(errorCode.getMessage());
        this.code=errorCode.getCode();
        this.description=description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
