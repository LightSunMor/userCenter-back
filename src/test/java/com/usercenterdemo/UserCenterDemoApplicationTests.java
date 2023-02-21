package com.usercenterdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserCenterDemoApplicationTests {

    @Test
    void contextLoads() {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(md5DigestAsHex);
    }

}
