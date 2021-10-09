package com.len.service;

import com.len.base.BaseService;
import com.len.entity.SysRole;
import com.len.util.LenResponse;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email lenospmiller@gmail.com
 */
public interface RoleService extends BaseService<SysRole, String> {



    void addRole(SysRole sysRole, String[] menus);

    void updateUser(SysRole role, String[] menus);

    void del(String id);
}
