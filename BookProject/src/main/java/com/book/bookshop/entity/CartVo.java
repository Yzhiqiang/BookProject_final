package com.book.bookshop.entity;

import lombok.Data;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 19:24 2021/10/12
 * @Modified By:
 */
@Data
public class CartVo {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private Integer count;
    private String bookName;
    private String imgUrl;
    private double newPrice;
}
