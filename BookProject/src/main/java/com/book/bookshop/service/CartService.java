package com.book.bookshop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.bookshop.entity.Cart;
import com.book.bookshop.entity.CartVo;
import com.book.bookshop.entity.User;
import com.book.bookshop.entity.UserCartVo;
import com.book.bookshop.mapper.CartMapper;
import com.book.bookshop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 18:24 2021/10/12
 * @Modified By:
 */
@Service
public class CartService extends ServiceImpl<CartMapper, Cart> {

    @Autowired
    private CartMapper cartMapper;

    public void InsertCart(User user , Cart cart)
    {
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",user.getId());
        cartQueryWrapper.eq("book_id",cart.getBookId());
        Cart  querycart = cartMapper.selectOne(cartQueryWrapper);
        if(querycart == null)
        {
            cartMapper.insert(cart);
        }else{
            querycart.setCount(cart.getCount() + querycart.getCount());
            cartMapper.updateById(querycart);
        }
    }

    /**
     * 根据用户查询购物车记录
     */
    public List<CartVo> findCartByUserId(Integer userId)
    {
        return cartMapper.findCartListByUserid(userId);
    }

    /**
     * 统计当前用户购物车的总计
     */
    public double getCartItemTotal(List<CartVo> list)
    {
        double sum = 0.0;
        for(CartVo cart: list)
        {
            sum += cart.getCount() * cart.getNewPrice();
        }
        return sum;
    }

    /**
     * 批量删除购物车
     * @param ids
     * @return
     */
    public String batchDelete(String ids) {
        if(ids != null){
            String []idArray = ids.split(",");
            cartMapper.deleteBatchIds(Arrays.asList(idArray));
        }
        return "success";
    }

    /**
     * 包装用户购物车信息
     */
    public UserCartVo wrapperCart(List<CartVo> list)
    {
        UserCartVo userCartVo = new UserCartVo();
        userCartVo.setNum(list.size());
        userCartVo.setTotalPrice(getCartItemTotal(list));
        return userCartVo;
    }


    /**
     * 根据购物车id查询对应的记录
     * @param
     * @return
     */
    public List<CartVo> findCartByIds(String ids)
    {
        return cartMapper.findCartListByIds(Arrays.asList(ids));
    }
}
