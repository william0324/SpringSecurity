package com.example.springbootsecurity.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName sys_menu
 */
@Data
public class authMenu implements Serializable {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 父级编号
     */
    private Integer pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 0代表菜单1权限2 url
     */
    private Integer type;

    /**
     * 0代表未删除，1 代表已删除
     */
    private Integer delete_flag;

    private static final long serialVersionUID = 1L;
}