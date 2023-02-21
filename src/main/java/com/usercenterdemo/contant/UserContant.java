package com.usercenterdemo.contant;

/**
 *  用户常量接口,相当于enum的作用
 *  属性默认psf
 */
public interface UserContant {
    //登录状态键
    String USER_LOGIN_STATE="loginUser";

    //----------用户状态---------
    // -管理员
    Integer ADMINISTRATOR=1;
    // -普通成员
    Integer COMMON_USER=0;

}
