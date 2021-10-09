package com.len.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

/**
 * @author zxm
 * @date 2021/3/23 21:35
 * <p>
 * Message Source 帮助类
 * 统一获取资源化 资源化配置在 resources/i18n目录下
 */


public class MsHelper {


    private static final MessageSource messageSource;

    static {
        messageSource = SpringUtil.getBean(MessageSource.class);
    }


    public static String getMsg(String msg) {
        return messageSource.getMessage(msg, null, LocalLocale.getLocale());
    }


    public static String getMessage(String s, Object[] objects, String s1) {
        return messageSource.getMessage(s, objects, s1, LocalLocale.getLocale());
    }

    public static String getMessage(String s, Object[] objects) throws NoSuchMessageException {
        return messageSource.getMessage(s, objects, LocalLocale.getLocale());
    }

    public static String getMessage(MessageSourceResolvable messageSourceResolvable) throws NoSuchMessageException {
        return messageSource.getMessage(messageSourceResolvable, LocalLocale.getLocale());
    }
}
