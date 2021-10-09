package com.len.controller;

import com.len.base.BaseController;
import com.len.base.CurrentUser;
import com.len.core.annotation.Log;
import com.len.core.shiro.Principal;
import com.len.entity.SysUser;
import com.len.service.SysUserService;
import com.len.util.Checkbox;
import com.len.util.LenResponse;
import com.len.util.MsHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2019-04-05.
 * @email lenospmiller@gmail.com
 */
@Controller
@RequestMapping("/person")
@Api(value = "个人业务", tags = "个人业务处理")
public class PersonController extends BaseController {

    @Autowired
    SysUserService userService;

    @GetMapping("/index")
    public String main() {
        return "/main/index";
    }

    @GetMapping()
    public String toPerson(Model model) {
        CurrentUser principal = Principal.getPrincipal();
        if (principal == null) {
            return "/login";
        }
        String id = principal.getId();
        SysUser user = userService.getById(id);
        model.addAttribute("user", user);
        return "/system/person/me";
    }

    @ApiOperation(value = "/updateUser", httpMethod = "POST", notes = "更新用户")
    @Log(desc = "更新用户", type = Log.LOG_TYPE.UPDATE)
    @PostMapping(value = "updateUser")
    @ResponseBody
    public LenResponse updatePerson(SysUser user) {
        userService.updatePerson(user);
        return succ(MsHelper.getMsg("update.success"));
    }
}
