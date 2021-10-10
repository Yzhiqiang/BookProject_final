package com.yu.shiro.realm;

import com.yu.entity.Perms;
import com.yu.entity.Role;
import com.yu.entity.User;
import com.yu.salt.MyByteSource;
import com.yu.util.ApplicationContextUtils;
import com.yu.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 19:32 2021/9/26
 * @Modified By:
 */
public class CustormerRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        String primaryPrincipal = (String) principal.getPrimaryPrincipal();      //获取主身份信息
        System.out.println(principal);
        System.out.println(primaryPrincipal);

//        根据主身份信息获取角色   和 权限信息
        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");
        User user = userService.findRolesByUsername(primaryPrincipal);
        if(!CollectionUtils.isEmpty(user.getRoles()))
        {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            for (Role role : user.getRoles()) {
                simpleAuthorizationInfo.addRole(role.getName());
                List<Perms> perms = userService.findPermsByRoled(role.getId());
                if(!CollectionUtils.isEmpty(perms))
                {
                    perms.forEach(perm->{
                        simpleAuthorizationInfo.addStringPermission(perm.getName());
                    });
                }

            }
            return simpleAuthorizationInfo;
        }

//        if("zhangsan".equals(primaryPrincipal))
//        {
//            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//            simpleAuthorizationInfo.addRole("admin");
//            simpleAuthorizationInfo.addStringPermission("user:add:*");
//            simpleAuthorizationInfo.addStringPermission("user:find:*");
//            return simpleAuthorizationInfo;
//        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("======================认证============================");
        String principle = (String) token.getPrincipal();     //获取身份信息，用户名

//        在工厂中获取service对象
        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");
        User user = userService.findByusername(principle);
        if(!ObjectUtils.isEmpty(user))
        {
            //传入随机盐，需要借助盐工具类
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), new MyByteSource(user.getSalt()),this.getName());
        }
        return null;
    }
}
