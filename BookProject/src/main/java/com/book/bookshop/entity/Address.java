package com.book.bookshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 13:48 2021/10/14
 * @ModifiedBy:
 */
@Data
@TableName(value="bs_address")
public class Address extends Model<Address> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String province;
    private String city;
    private String area;
    private String detailAddress;
    private String receiver;
    private String emailCode;
    private String tel;
    private String isDefault;           //是否是默认收获地址
}
