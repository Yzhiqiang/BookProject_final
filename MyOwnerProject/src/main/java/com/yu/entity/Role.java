package com.yu.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 16:57 2021/10/9
 * @Modified By:
 */
@Data
public class Role {
    private int id;
    private String name;
    private List<Perms> perms;
}
