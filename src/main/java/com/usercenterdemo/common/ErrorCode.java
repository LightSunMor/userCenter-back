package com.usercenterdemo.common;

/**
 *  错误码 当请求错误的时候，规范 返回当前错误原因
 *   在下面定义可能需要的错误码
 * @author MorSun
 */
public enum ErrorCode {

    SUCCESS(200,"success",""),
    PARAMS_ERROR(40000,"请求参数错误",""), //400 表示前端传来的参数问题，这里使用40000格式的业务码表示错误码
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"没有权限",""),
    SYSTEM_ERROR(50000,"系统内部异常","")
    ;

    /**
     *  状态码信息,基本不会改变，定位final
     */
    private final int code;

    /**
     *  状态码描述
     */
    private final String message;

    /**
     *  补充描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
