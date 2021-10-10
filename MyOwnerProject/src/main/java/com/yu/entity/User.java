package com.yu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 16:57 2021/10/9
 * @Modified By:
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String salt;
    private List<Role> Roles;
}
