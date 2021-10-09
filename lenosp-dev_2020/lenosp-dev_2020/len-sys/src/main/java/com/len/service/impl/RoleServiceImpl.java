package com.len.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysRole;
import com.len.entity.SysRoleMenu;
import com.len.entity.SysRoleUser;
import com.len.exception.ServiceException;
import com.len.mapper.SysRoleMapper;
import com.len.service.RoleMenuService;
import com.len.service.RoleService;
import com.len.service.RoleUserService;
import com.len.util.BeanUtil;
import com.len.util.LenResponse;
import com.len.util.MsHelper;
import com.len.validator.ValidatorUtils;
import com.len.validator.group.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email lenospmiller@gmail.com
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<SysRole, String> implements RoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleUserService roleUserService;


    @Override
    public void addRole(SysRole sysRole, String[] menus) {
        ValidatorUtils.validateEntity(sysRole, AddGroup.class);
        roleMapper.insert(sysRole);
        //操作role-menu data
        if (menus != null) {
            for (String menu : menus) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getId());
                sysRoleMenu.setMenuId(menu);
                roleMenuService.save(sysRoleMenu);
            }
        }
    }

    @Override
    public void updateUser(SysRole role, String[] menus) {
        LenResponse jsonUtil = new LenResponse();
        jsonUtil.setFlag(false);
        SysRole oldRole = roleMapper.selectById(role.getId());
        BeanUtil.copyNotNullBean(role, oldRole);
        roleMapper.updateById(oldRole);

        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setRoleId(role.getId());
        List<SysRoleMenu> menuList = roleMenuService.selectByCondition(sysRoleMenu);
        for (SysRoleMenu sysRoleMenu1 : menuList) {
            roleMenuService.deleteByPrimaryKey(sysRoleMenu1);
        }
        if (menus != null) {
            for (String menu : menus) {
                sysRoleMenu.setMenuId(menu);
                roleMenuService.save(sysRoleMenu);
            }
        }
    }

    @Override
    public void del(String id) {
        ValidatorUtils.notEmpty(id,"failed.get.data");
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setRoleId(id);
        QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>(sysRoleUser);
        int count = roleUserService.count(wrapper);
        if (count > 0) {
            throw new ServiceException(MsHelper.getMsg("role.exists.user"));
        }
        roleMapper.deleteById(id);
    }
}
