package com.book.bookshop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.bookshop.entity.User;
import com.book.bookshop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 13:49 2021/10/11
 * @Modified By:
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    /**
     * 验证用户的存在
     */
    @Autowired
    private UserMapper userMapper;

    public String checkUser(String username)
    {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) return "101";
        else return "102";
    }

    /**
     * 登录验证
     */
    public String loginCheck(User loginuser, HttpSession session)
    {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",loginuser.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if(user == null)
        {
            return "101";
        }else{
            if(loginuser.getPassword().equals(user.getPassword())){
                session.setAttribute("user", user);
                return "100";
            }else return "102";
        }
    }
}
