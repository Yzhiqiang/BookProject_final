package com.yu.controller;

import com.yu.entity.User;
import com.yu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 20:00 2021/9/26
 * @Modified By:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService useService;

    /**
     *
     * @param user
     * @return
     */
    @RequestMapping("register")
    public String register(User user)
    {
        try {
            System.out.println("===================================================");
            useService.register(user);
            return "redirect:/login.jsp";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/register.jsp";
    }
    }

    @RequestMapping("login")
    public String login(String username, String password)
    {
//        获取主体对象
        Subject subject = SecurityUtils.getSubject();    //只要我们定一个了完全管理器，就会自动给安全工具类中注入安全管理器
        try {
            subject.login(new UsernamePasswordToken(username, password));
            return "redirect:/index.jsp";
        } catch (UnknownAccountException e) {
            System.out.println("用户名错误！！！！");
            e.printStackTrace();
        }catch(IncorrectCredentialsException e)
        {
            System.out.println("密码错误！！！！");
            e.printStackTrace();
        }
        return "redirect:/login.jsp";
    }

    /*
            退出登录
     */
    @RequestMapping("/logout")
    public String logout()
    {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();    //退出用户
        return "redirect:/login.jsp";
    }

    @RequestMapping("/test")
    public String test()
    {
        return "test";
    }


}
