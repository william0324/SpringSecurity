package com.example.springbootsecurity.mapper;

import com.example.springbootsecurity.pojo.User;
import org.springframework.data.repository.query.Param;

/**
* @author William
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2023-09-17 22:51:13
* @Entity com.example.springbootsecurity.pojo.User
*/
public interface UserMapper {
    public User selectAllByUsername(@Param("username") String username);
}




