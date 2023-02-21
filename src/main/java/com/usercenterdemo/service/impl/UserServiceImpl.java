package com.usercenterdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usercenterdemo.common.ErrorCode;
import com.usercenterdemo.contant.UserContant;
import com.usercenterdemo.exception.BusinessException;
import com.usercenterdemo.model.domain.User;
import com.usercenterdemo.service.UserService;
import com.usercenterdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author MorSun
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2022-09-22 18:48:27
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    //密码加密，加盐混淆
    private static final String Salt="sunLight";

    @Override
    public int userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)) //使用lang包中的StringUtils类中的isAnyBlank判断多个字段是否为空
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if (userAccount.length()<4)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名太短");
        if (userPassword.length()<8||checkPassword.length()<8)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码太短");
        }
        if (planetCode.length()>5||planetCode.hashCode()<="0".hashCode())
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号太长");
        //账户不包含特殊字符(正则)
        String regex="\\W"; // 匹配单词字符
        Matcher m=Pattern.compile(regex).matcher(userAccount);
        if (m.find())
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户包含不符合要求字符");
        //密码和校验密码是否相同
        if (!userPassword.equals(checkPassword))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"二次确认密码不相同");
        //账户不能重复 ，重复-1
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        int i = this.count(queryWrapper);
        if (i>0)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户重复");
        //,编号也不能重复 重复-2
        QueryWrapper<User> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("planetCode",planetCode);
        int k = this.count(queryWrapper);
        if (k>0)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号重复");
        //对密码进行加密(加盐加密)
        String md5DigestAsHex = DigestUtils.md5DigestAsHex((Salt+userPassword).getBytes());

        //插入对象
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(md5DigestAsHex); //密码
        user.setPlanetCode(planetCode); //编号

        boolean save = this.save(user);
        if (!save)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"无法存入新用户，估计重复用户");
        }
        return user.getIds();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //校验密码和账户
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword)) //使用lang包中的StringUtils类中的isAnyBlank判断多个字段是否为空
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空");
        }
        if (userAccount.length()<4)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户过短");
        if (userPassword.length()<8)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码过短");
        }
        //账户不包含特殊字符(正则)
        String regex="\\W"; // 匹配单词字符
        Matcher m=Pattern.compile(regex).matcher(userAccount);
        if (m.find())
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户包含不符合要求字符");
        //对密码进行加密(加盐加密)
        String md5DigestAsHex = DigestUtils.md5DigestAsHex((Salt+userPassword).getBytes());

        //查询用户是否存在
        QueryWrapper<User> queryWrapper=new QueryWrapper<>(); //泛型是实体
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",md5DigestAsHex);
        User one = this.getOne(queryWrapper);
        //用户不存在
        if (one==null){
            log.info("user login failed,userAccount cannot match passWord");
            throw new BusinessException(ErrorCode.NULL_ERROR,"账号或密码错误");
        }
        // todo:（暂时不设计）用户在单个ip机器登录次数太多，限制一下该账号的状态！！！

        //用户脱敏 (建议新建对象进行设置 )
        User safetyUser=getSafetyUser(one);
        //记录用户的登录态，记得设计超时时间
        HttpSession session = request.getSession(); //底层使用map存储数据
        session.setAttribute(UserContant.USER_LOGIN_STATE,safetyUser);
        return safetyUser;
    }

    /**
     *  用户注销
     * @param request
     */
    @Override
    public void UserLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(UserContant.USER_LOGIN_STATE);
        log.info(user.getUserAccount()+"退出登录");
        session.removeAttribute(UserContant.USER_LOGIN_STATE);
    }

    /**
     *  用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser)
    {
        if (originUser==null)
            return null;
        User safetyUser = new User();
        safetyUser.setIds(originUser.getIds());
        safetyUser.setNickName(originUser.getNickName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatar(originUser.getAvatar());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        return originUser;
    }

}




