package com.usercenterdemo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体  就相当于替代user的位置，因为用不上user中那么多值，就自己封装一个
 * @author sunLight
 */
@Data
public class UserRegisterRequest implements Serializable {
    //生成序列化id，需要实现序列化接口，安装插件generalVersionUID
    //用于序列化Json过程中发生冲突
    private static final long serialVersionUID = 8815395056216056557L;

    //定义一下前端传入的参数
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
