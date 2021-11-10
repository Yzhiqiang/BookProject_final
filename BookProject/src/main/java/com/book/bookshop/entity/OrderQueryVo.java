package com.book.bookshop.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: jzhang
 * @Date: 2019/10/9 09:21
 * @Description: 订单查询条件包装类
 */
@Data
public class OrderQueryVo {
    private Integer page;
    private Integer pageSize;
    private String orderNum;
    private String tradeStatus;
    private String orderBy;
    private Integer userId;

    //分页起始位置
    private Integer begin;
    //分页结束位置
    private Integer end;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
