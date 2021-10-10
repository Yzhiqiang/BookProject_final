package com.yu.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 9:51 2021/9/27
 * @Modified By:
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    //    根据bean的名字获取工厂中指定的bean
    public static Object getBean(String beanName)
    {
        return context.getBean(beanName);
    }

}
