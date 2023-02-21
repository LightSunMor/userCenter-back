package com.usercenterdemo.mapper;

import com.usercenterdemo.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86176
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2022-09-22 18:48:27
* @Entity com.usercenterdemo.model.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




