package com.len.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.len.base.BaseController;
import com.len.core.annotation.Log;
import com.len.core.annotation.Log.LOG_TYPE;
import com.len.entity.SysMenu;
import com.len.service.MenuService;
import com.len.util.BeanUtil;
import com.len.util.LenResponse;
import com.len.util.MsHelper;
import com.len.util.ReType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhuxiaomeng
 * @date 2017/12/13.
 * @email lenospmiller@gmail.com
 * 菜单
 */
@RequestMapping("/menu")
@Controller
@Api(value = "菜单管理", tags = "菜单业务处理")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @GetMapping(value = "showMenu")
    public String showUser() {
        return "/system/menu/menuList";
    }

    @GetMapping(value = "showMenuList")
    @ResponseBody
    public ReType showUser(String name) {
        SysMenu sysMenu = new SysMenu();
        if (!StringUtils.isEmpty(name)) {
            sysMenu.setName(name);
        }
        return new ReType(10, menuService.list(new QueryWrapper<>(sysMenu)));
    }

    @GetMapping(value = "showAddMenu")
    public String addMenu(Model model) {
        JSONArray ja = menuService.getMenuJsonList();
        model.addAttribute("menus", ja.toJSONString());
        return "/system/menu/add";
    }

    @Log(desc = "添加菜单", type = LOG_TYPE.UPDATE)
    @ApiOperation(value = "/addMenu", httpMethod = "POST", notes = "添加菜单")
    @PostMapping(value = "addMenu")
    @ResponseBody
    public LenResponse addMenu(SysMenu sysMenu) {
        if (StringUtils.isEmpty(sysMenu.getPId())) {
            sysMenu.setPId(null);
        }
        if (StringUtils.isEmpty(sysMenu.getUrl())) {
            sysMenu.setUrl(null);
        }
        if (StringUtils.isEmpty(sysMenu.getPermission())) {
            sysMenu.setPermission(null);
        }
        if (sysMenu.getMenuType() == 2) {
            sysMenu.setMenuType((byte) 0);
        }
        menuService.save(sysMenu);
        return succ(MsHelper.getMsg("insert.success"));
    }

    @GetMapping(value = "showUpdateMenu")
    public String showUpdateMenu(Model model, String id) {
        SysMenu sysMenu = menuService.getById(id);
        JSONArray ja = menuService.getMenuJsonList();
        model.addAttribute("menus", ja.toJSONString());
        model.addAttribute("sysMenu", sysMenu);
        if (null != sysMenu.getPId()) {
            SysMenu pSysMenu = menuService.getById(sysMenu.getPId());
            model.addAttribute("pName", pSysMenu.getName());
        }
        return "/system/menu/update";
    }


    @Log(desc = "更新菜单", type = LOG_TYPE.ADD)
    @PostMapping(value = "updateMenu")
    @ResponseBody
    public LenResponse updateMenu(SysMenu sysMenu) {
        SysMenu oldMenu = menuService.getById(sysMenu.getId());
        BeanUtil.copyNotNullBean(sysMenu, oldMenu);
        menuService.updateById(oldMenu);
        return succ(MsHelper.getMsg("update.success"));
    }

    @Log(desc = "删除菜单", type = LOG_TYPE.DEL)
    @PostMapping("del")
    @ResponseBody
    public LenResponse del(String id) {
        menuService.del(id);
        return succ(MsHelper.getMsg("del.success"));
    }

}
