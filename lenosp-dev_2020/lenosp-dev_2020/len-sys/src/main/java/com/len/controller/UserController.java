package com.len.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.core.annotation.Log;
import com.len.core.annotation.Log.LOG_TYPE;
import com.len.core.quartz.JobTask;
import com.len.entity.SysUser;
import com.len.exception.ServiceException;
import com.len.service.RoleUserService;
import com.len.service.SysUserService;
import com.len.util.*;
import com.len.validator.ValidatorUtils;
import com.len.validator.group.AddGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/6.
 * @email lenospmiller@gmail.com
 * 用户管理
 */
//@Api(value="user")
@Controller
@RequestMapping(value = "/user")
@Api(value = "用户管理", tags = "用户管理业务")
public class UserController extends BaseController {

    //private static final Logger

    @Autowired
    SysUserService userService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    JobTask task;

    @GetMapping(value = "mainTest")
    @RequiresPermissions("user:show")
    public String showTest() {
        return "system/user/mainTest";
    }

    @GetMapping(value = "showUser")
    @RequiresPermissions("user:show")
    public String showUser() {
        return "/system/user/userList";
    }

    @GetMapping(value = "showUserList")
    @ResponseBody
    @RequiresPermissions("user:show")
    public ReType showUser(SysUser user, String page, String limit, Principal principal) {
        return userService.show(user, Integer.valueOf(page), Integer.valueOf(limit));
    }

    @ApiOperation(value = "/listByRoleId", httpMethod = "GET", notes = "展示角色")
    @GetMapping(value = "listByRoleId")
    @ResponseBody
    @RequiresPermissions("user:show")
    public String showUser(String roleId, int page, int limit) {
        JSONObject returnValue = new JSONObject();
        Page<Object> startPage = PageHelper.startPage(page, limit);
        List<SysUser> users = userService.getUserByRoleId(roleId);
        returnValue.put("users", users);
        returnValue.put("totals", startPage.getTotal());
        return JSON.toJSONString(returnValue);
    }


    @GetMapping(value = "showAddUser")
    public String goAddUser(Model model) {
        List<Checkbox> checkboxList = userService.getUserRoleList(null);
        model.addAttribute("boxJson", checkboxList);
        return "/system/user/add";
    }

    @ApiOperation(value = "/addUser", httpMethod = "POST", notes = "添加用户")
    @Log(desc = "添加用户")
    @PostMapping(value = "addUser")
    @ResponseBody
    public LenResponse addUser(SysUser user, String[] role) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        if (role == null) {
            return error(MsHelper.getMsg("user.select.role"));
        }
        userService.add(user, Arrays.asList(role));
        return succ();
    }

    @GetMapping(value = "updateUser")
    public String goUpdateUser(String id, Model model, boolean detail) {
        ValidatorUtils.notEmpty(id, "failed.get.data");
        //用户-角色
        SysUser user = userService.getById(id);
        if (user != null) {
            String photo = user.getPhoto();
            if (StringUtils.isEmpty(photo)) {
                user.setPhoto(FileUtil.defaultPhoto);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("boxJson", userService.getUserRoleList(id));
        model.addAttribute("detail", detail);
        return "system/user/update";
    }

    public String userDetail(String id, Model model) {
        List<Checkbox> checkboxList = userService.getUserRoleList(id);
        SysUser user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("boxJson", checkboxList);
        return "system/user/update";
    }


    @ApiOperation(value = "/updateUser", httpMethod = "POST", notes = "更新用户")
    @Log(desc = "更新用户", type = LOG_TYPE.UPDATE)
    @PostMapping(value = "updateUser")
    @ResponseBody
    public LenResponse updateUser(SysUser user, String[] role) {
        List<String> roles = new ArrayList<>();
        if (role != null) {
            roles = Arrays.asList(role);
        }
        userService.updateUser(user, roles);
        return succ(MsHelper.getMsg("update.success"));
    }

    @Log(desc = "删除用户", type = LOG_TYPE.DEL)
    @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除用户")
    @PostMapping(value = "/del")
    @ResponseBody
    @RequiresPermissions("user:del")
    public LenResponse del(String id, boolean realDel) {
        userService.delById(id, realDel);
        return succ(MsHelper.getMsg("del.success"));
    }

    @GetMapping(value = "goRePass")
    public String goRePass(String id, Model model) {
        ValidatorUtils.notEmpty(id, "failed.get.data1", "id");
        model.addAttribute("user", userService.getById(id));
        return "/system/user/resetPassword";
    }

    /**
     * 修改密码
     *
     * @param id
     * @param newPwd
     * @return
     */
    @Log(desc = "修改密码", type = LOG_TYPE.UPDATE)
    @PostMapping(value = "rePass")
    @ResponseBody
    @RequiresPermissions("user:repass")
    public LenResponse rePass(String id, String newPwd) {
        userService.rePass(id, newPwd);
        return succ(MsHelper.getMsg("update.success"));
    }

    @Autowired
    UploadUtil uploadUtil;

    /**
     * 头像上传 目前首先相对路径
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public LenResponse imgUpload(@RequestParam("file") MultipartFile file) {
        return succ(uploadUtil.upload(file));
    }

    /**
     * 验证用户名是否存在
     */
    @GetMapping(value = "checkUser")
    @ResponseBody
    public LenResponse checkUser(String uname) {
        if (StringUtils.isEmpty(uname)) {
            throw new ServiceException(MsHelper.getMsg("failed.get.data"));
        }
        if (userService.checkUser(uname) > 0) {
            throw new ServiceException(MsHelper.getMsg("user.exists"));
        }
        return succ();
    }


}
