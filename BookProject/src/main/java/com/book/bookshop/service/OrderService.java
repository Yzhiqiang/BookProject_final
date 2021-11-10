package com.book.bookshop.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.bookshop.entity.*;
import com.book.bookshop.mapper.OrderItemMapper;
import com.book.bookshop.mapper.OrderMapper;
import com.book.bookshop.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 16:23 2021/10/14
 * @Modified By:
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    CartService cartService;
    /**
     * 购买商品
     * @param cartVos
     * @param addrId
     * @param session
     * @return
     */

    public String buy(List<CartVo> cartVos, Integer addrId, HttpSession session) {
        //1.生成订单表记录
        User user = (User) session.getAttribute("user");
        Order order = new Order();
        order.setAddressId(addrId);
        order.setUserId(user.getId());
        order.setCreateDate(new Date());
//        order.setOrderNum(UUID.randomUUID().toString());
        order.setOrderNum(OrderUtils.createOrderNum());

        order.setOrderStatus("1");
        orderMapper.insert(order);
        //2.生成订单明细表记录
        OrderItem orderItem = new OrderItem();
        for (CartVo cartVo : cartVos) {
            orderItem.setBookId(cartVo.getBookId());
            orderItem.setCount(cartVo.getCount());
            orderItem.setOrderId(order.getId());
            orderItemService.save(orderItem);
            //3.删除购物车表中记录
            cartService.removeById(cartVo.getId());
        }
        return "success";
    }

    /**
     * 查询用户订单
     */
    public List<Order> findUserOrder(Integer userId,  OrderQueryVo orderQueryVo)
    {
        orderQueryVo.setBegin((orderQueryVo.getPage() - 1) * orderQueryVo.getPageSize());
        orderQueryVo.setEnd(orderQueryVo.getPage() * orderQueryVo.getPageSize());
        orderQueryVo.setUserId(userId);
        List<Order> list = orderMapper.findOrderAndDetailListByUserId(orderQueryVo);
        for(Order order:list)
        {
            List<OrderItem> items = order.getOrderItems();
            double price = 0.0;
            for(OrderItem item:items)
            {
                price += item.getCount() * item.getBook().getNewPrice();
            }
            order.setTotalPrice(price);
        }
        return list;
    }


    /**
     * 查询总页数
     */
    public Integer findUserOrderPages(OrderQueryVo orderQueryVo){
        orderQueryVo.setUserId(orderQueryVo.getPage());
        int count = orderMapper.findOrderCountByUser(orderQueryVo);
        return (count - 1) / orderQueryVo.getPageSize() + 1;
    }


}
