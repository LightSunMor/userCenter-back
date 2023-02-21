package com.usercenterdemo.service;

import com.usercenterdemo.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author 86176
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2022-09-22 18:48:27
*/
@Service
public interface UserService extends IService<User> {
    /**
     *  用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return 新用户ids
     */
    int userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);

    /**
     *  用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return 用户（已脱敏）
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     *  用户注销
     * @param request
     */
    void UserLogout(HttpServletRequest request);
    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
}
