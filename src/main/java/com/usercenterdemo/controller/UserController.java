package com.usercenterdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.usercenterdemo.common.BaseResponse;
import com.usercenterdemo.common.ErrorCode;
import com.usercenterdemo.common.ResultUtils;
import com.usercenterdemo.contant.UserContant;
import com.usercenterdemo.exception.BusinessException;
import com.usercenterdemo.model.domain.User;
import com.usercenterdemo.model.request.UserLoginRequest;
import com.usercenterdemo.model.request.UserRegisterRequest;
import com.usercenterdemo.service.UserService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* 用户层接口*/
@RestController // ResponeBody 和 Controller结合注解
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    /**
     *  用户注册 ✔
     * @param userRegisterRequest
     * @return
     * @athour sunLight
     */
    @PostMapping("/register")
    public BaseResponse<Integer> userRegister(@RequestBody UserRegisterRequest userRegisterRequest)
    {
        if (userRegisterRequest==null)
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String palnetCode=userRegisterRequest.getPlanetCode();
        //简单校验数据本身真实姓（controller层倾向）
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,palnetCode))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数空异常");
        int i = userService.userRegister(userAccount, userPassword, checkPassword, palnetCode);
//        return new BaseResponse<Integer>(200,i,"register success");
        return ResultUtils.success(i);
    }

    /**
     *  用户登录 ✔
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request)
    {
        if (userLoginRequest==null)
            throw new BusinessException(ErrorCode.NULL_ERROR,"参数为空");
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //简单校验数据本身真实性（controller层倾向）
        if (StringUtils.isAnyBlank(userAccount,userPassword))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数空异常");
        User userLogin = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(userLogin);
    }

    /**
     *  用户退出 ✔
     * @param request
     * @return 是否成功字符串
     * @author MorSun
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogOut(HttpServletRequest request)
    {
        if (request==null)
            throw new BusinessException(ErrorCode.NOT_LOGIN,"用户未登录");
        userService.UserLogout(request);
        return ResultUtils.success(ErrorCode.SUCCESS);
    }

    /**
     *  获取当前登录用户态 ✔
     * @return 用户信息
     * @author MorSun
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request)
    {
        User curr = (User)request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        //未登录
        if (curr==null)
            throw new BusinessException(ErrorCode.NOT_LOGIN,"user为空，用户未登录");
        //拿取最新用户态
        User one = userService.getById(curr.getIds()); // TODO :后期考虑到多重因素，账户状态是否失效等等，再取用户信息（后期做）
        User safetyUser = userService.getSafetyUser(one);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 根据名字（或无名）查询用户 ✔
     * @param username
     * @return 返回一个用户列表
     * @athour sunLight
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request) // 在username前不加@requestParam ,表示没有username传过来也是可以的
        {
            if (request.getSession().getAttribute(UserContant.USER_LOGIN_STATE)==null)
                throw new BusinessException(ErrorCode.NULL_ERROR,"请先登录");
        if (!isAdmin(request))
        {
            throw new BusinessException(ErrorCode.NO_AUTH,"用户无权限");
        }
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) //不为空，不为“”，不为空字符
        {
            queryWrapper.like("nickName",username); //查源码可知，like是模糊查询 还有likeLeft等
        }
        //如果username为空，那么直接无要求查询用户信息
        //处理返回user集合，并且进行脱敏
        List<User> userList = userService.list(queryWrapper);
        return ResultUtils.success(userList.stream().map(user -> userService.getSafetyUser(user) ).collect(Collectors.toList())) ;
    }
    /**
     *  用户删除
     * @param ids
     * @return boolean类型
     */
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> delUsers(@RequestParam Integer ids,HttpServletRequest request)
    {
        if (!isAdmin(request))
            throw new BusinessException(ErrorCode.NO_AUTH,"用户无权限");
        if (ids<=0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"ids数据小于等于0");
        }
        return ResultUtils.success(userService.removeById(ids)); //逻辑删除
    }

    /**
     *  是否为管理员 ✔
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        if (user!=null&&user.getUserRole()==UserContant.ADMINISTRATOR)
            return true;
        else
            return false;
    }

}
