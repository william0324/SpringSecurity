package com.example.springbootsecurity.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName sys_user
 */
@Data
public class User implements Serializable {
    /**
     * 编号
     */
    private Integer user_id;

    /**
     * 登陆名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 地址
     */
    private String address;

    /**
     * 是否启动账户0禁用 1启用
     */
    private Integer enabled;

    /**
     * 账户是否没有过期0已过期 1 正常
     */
    private Integer account_no_expired;

    /**
     * 密码是否没有过期0已过期 1 正常
     */
    private Integer credentials_no_expired;

    /**
     * 账户是否没有锁定0已锁定 1 正常
     */
    private Integer account_no_locked;

    private static final long serialVersionUID = 1L;
}