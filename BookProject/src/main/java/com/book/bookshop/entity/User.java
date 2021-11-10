package com.book.bookshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther: jzhang
 * @Date: 2019/9/24 15:38
 * @Description: 用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "bs_user")
public class User extends Model<User> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String company;
}
