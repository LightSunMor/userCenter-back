package com.usercenterdemo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 * @author sunLight
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -3059667343222045328L;
    private String userAccount;
    private String userPassword;
}
