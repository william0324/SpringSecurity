package com.example.springbootsecurity.mapper;

import org.springframework.data.repository.query.Param;

import java.util.List;

/**
* @author William
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2023-09-18 00:24:29
* @Entity com.example.springbootsecurity.pojo.authMenu
*/
public interface AuthMenuMapper {
    public List<String> selectCodeByPidString(@Param("userId") Integer userId);
}




