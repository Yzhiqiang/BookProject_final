package com.book.bookshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.bookshop.entity.Order;
import com.book.bookshop.entity.OrderQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 16:22 2021/10/14
 * @Modified By:
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    //根据用户id查询用户订单以及订单明细
//    List<Order> findOrderAndDetailListByUserId(@Param("userId") Integer userId, @Param("begin")Integer begin, @Param("end")Integer end);
    List<Order> findOrderAndDetailListByUserId(OrderQueryVo orderQueryVo);


    /**
     * 查询记录总数
     */
    Integer findOrderCountByUser(OrderQueryVo orderQueryVo);
}
