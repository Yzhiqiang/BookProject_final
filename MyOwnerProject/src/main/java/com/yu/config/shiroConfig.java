package com.yu.config;

import com.yu.cache.RedisCacheManager;
import com.yu.shiro.realm.CustormerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 21:25 2021/10/9
 * @Modified By:
 */
@Configuration
public class shiroConfig {



    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);    //设置安全管理器

//        配置系统的受限资源
        Map<String ,String> map = new HashMap<String ,String>();

        /*
                不受限的资源要放到上面
         */
        map.put("/user/**","anon");    //anon设置为公共资源，不需要进行认证
        map.put("/register.jsp","anon");

        //  如果不将/user/login设置成anon，每次用户登录，还没进行认证玩
        map.put("/**","authc");          //请求这个资源时需要认证和授权,shiro中提供的默认的过滤器的简称

//        默认认证界面路径,默认就是login.jsp
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    //    2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm)
    {
        DefaultWebSecurityManager defaultWebSecurityManagr = new DefaultWebSecurityManager();
        defaultWebSecurityManagr.setRealm(realm);
        return defaultWebSecurityManagr;
    }

    /**
     * Reaml除了可以修改凭证匹配器之外，还可以开启缓存管理
     * @return
     */
    //    3.创建自定义的Realm
    @Bean
    public Realm getRealm()
    {
        CustormerRealm custormerRealm = new CustormerRealm();
        //修改凭证匹配器,默认的是无密码凭证匹配器，所以应该换成hash凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        custormerRealm.setCredentialsMatcher(credentialsMatcher);

//        开启缓存管理
        custormerRealm.setCacheManager(new RedisCacheManager());
        custormerRealm.setCachingEnabled(true);   //开启缓存管理
        custormerRealm.setAuthenticationCachingEnabled(true);      //开启认证缓存管理
        custormerRealm.setAuthenticationCacheName("authenticationCache");
        custormerRealm.setAuthorizationCachingEnabled(true);        //开启授权缓存管理
        custormerRealm.setAuthorizationCacheName("authorizationCache");


        return custormerRealm;
    }
}
