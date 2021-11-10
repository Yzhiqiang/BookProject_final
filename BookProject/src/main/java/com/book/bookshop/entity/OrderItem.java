package com.book.bookshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 16:17 2021/10/14
 * @Modified By:
 */
@Data
@TableName("bs_order_item")
public class OrderItem extends Model<OrderItem> {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer orderId;
    private Integer bookId;
    private Integer count;

    //图书对象
    @TableField(exist = false)
    private Book book;
}
