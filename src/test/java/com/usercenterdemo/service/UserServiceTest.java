package com.usercenterdemo.service;
import java.util.Date;

import com.usercenterdemo.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
/*
用户服务测试
* */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService service;
    @Test
    public void testAdd()
    {
        //自动生成后，删除数据库已设置的默认值的字段
        User user = new User();
        user.setNickName("sunLight");
        user.setUserAccount("sun");
        user.setUserPassword("123456");
        //从网上找到一个图像链接
        user.setAvatar("https://img2.baidu.com/it/u=390829681,3002818272&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        user.setGender(0);
        user.setPhone("17623474077");
        user.setEmail("852364");
        boolean b = service.save(user);
        //mybatis自动从数据库拿到默认生成的值塞回user
        System.out.println(user.getIds());
        //断言
        assertTrue(b);

    }

    @Test
    void userRegister() {
        int register = service.userRegister("", "123456789", "123456789","1");
        Assertions.assertEquals(-1,register,"账户为空");
        int register1 = service.userRegister("lig", "123456789", "123456789","1");
        Assertions.assertEquals(-1,register1,"账户不小于4位");
        int register2 = service.userRegister("light", "123456789", "12345678","1");
        Assertions.assertEquals(-1,register2,"密码和校验密码不同");
        int register3 = service.userRegister("light&%", "123456789", "123456789","1");
        Assertions.assertEquals(-1,register3,"账户的格式不符合要求");
        int register4 = service.userRegister("light", "123456789", "123456789","1");
        Assertions.assertTrue(register4>0);
    }
}