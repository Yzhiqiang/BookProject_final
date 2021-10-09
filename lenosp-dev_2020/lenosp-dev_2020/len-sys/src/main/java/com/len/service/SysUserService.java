package com.len.service;


import com.len.base.BaseService;
import com.len.entity.SysRoleUser;
import com.len.entity.SysUser;
import com.len.util.Checkbox;
import com.len.util.LenResponse;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/4.
 * @email lenospmiller@gmail.com
 */
public interface SysUserService extends BaseService<SysUser, String> {

    SysUser login(String username);

    /**
     * 新增用户和用户角色信息
     *
     * @param user 用户对象
     * @param role 角色列表
     * @return
     */
    boolean add(SysUser user, List<String> role);

    /**
     * 更新用户和用户角色信息
     * @param user
     * @param role
     * @return
     */
    boolean updateUser(SysUser user, List<String> role);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delById(String id, boolean flag);

    int checkUser(String username);


    List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);

    List<Checkbox> getUserRoleList(String id);

    /**
     * 更新密码
     * @param id 用户id
     * @param newPwd 新密码
     * @return
     */
    int rePass(String id, String newPwd);


    List<SysUser> getUserByRoleId(String roleId);

    void setMenuAndRoles(String username);

    void updateCurrent(SysUser user);

    boolean updatePerson(SysUser user);
}
