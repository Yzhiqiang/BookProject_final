package com.book.bookshop.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.bookshop.entity.OrderItem;
import com.book.bookshop.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 16:24 2021/10/14
 * @Modified By:
 */
@Service
public class OrderItemService extends ServiceImpl<OrderItemMapper, OrderItem> {
}
