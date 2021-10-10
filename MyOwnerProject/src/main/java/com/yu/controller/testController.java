package com.yu.controller;

import com.yu.config.testConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 15:31 2021/10/10
 * @Modified By:
 */
@Controller
public class testController {

    @Autowired
    testConfig testconfig;


    @RequestMapping("/hello")
    public String hello()
    {
        return testconfig.getUsername();
    }
}
