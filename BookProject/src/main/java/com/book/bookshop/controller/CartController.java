package com.book.bookshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.bookshop.entity.Cart;
import com.book.bookshop.entity.CartVo;
import com.book.bookshop.entity.User;
import com.book.bookshop.entity.UserCartVo;
import com.book.bookshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 17:36 2021/10/12
 * @Modified By:
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @ResponseBody
    @RequestMapping("/add")
    public String add(Cart cart, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        cart.setUserId(user.getId());
        //判断数据库中是否已经存在购物车了
        cartService.InsertCart(user, cart);
        return "success";
    }

    /**
     * 查询当前用户的购物车
     */
    @RequestMapping("/list")
    public String list(HttpSession session, Model model)
    {
        User user = (User) session.getAttribute("user");
        List<CartVo> cartVoList = cartService.findCartByUserId(user.getId());
        //将用户的购物车信息存放到session中
        UserCartVo userCartVo = cartService.wrapperCart(cartVoList);
        session.setAttribute("userCartInfo",userCartVo);

        model.addAttribute("cartList",cartVoList);
        return "cart";
    }

    /**
     * 更新购物车信息
     */
    @ResponseBody
    @RequestMapping("/update")
    public String update(HttpSession session, Cart cart)
    {
        cartService.updateById(cart);
        User user = (User) session.getAttribute("user");
        List<CartVo> cartVoList = cartService.findCartByUserId(user.getId());
        UserCartVo userCartVo = cartService.wrapperCart(cartVoList);
        session.setAttribute("userCartInfo",userCartVo);
        double price = cartService.getCartItemTotal(cartVoList);
        return String.valueOf(price);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String delete(String ids)
    {
        return cartService.batchDelete(ids);
    }
}
