package com.usercenterdemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.NestingKind;
import java.io.Serializable;

/**
 *  基本通用返回对象
 * @author MorSun
 */
@Data
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;
    private String description;


    public BaseResponse(int code,T data)
    {
        this.code=code;
        this.data=data;
    }

    public BaseResponse(int code,T data, String message) {
        this.code = code;
        this.data=data;
        this.message = message;
    }

    /**
     *  在通用返回对象中，使用到规范的错误码
     * @author Morsun
     */
    public BaseResponse(ErrorCode errorCode)
    {
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }

    public BaseResponse(ErrorCode errorCode, String description)
    {
        this(errorCode.getCode(),null,errorCode.getMessage(),description);
    }

}
