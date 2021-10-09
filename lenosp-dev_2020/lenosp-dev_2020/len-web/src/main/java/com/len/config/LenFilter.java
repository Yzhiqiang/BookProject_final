package com.len.config;

import com.len.service.SysUserService;
import com.len.util.LocalLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@Component
public class LenFilter implements Filter {

    @Autowired
    SysUserService sysUserService;

    /**
     * 支持语言 扩展语言可直接在此添加
     */
    private static Locale[] supportLocales = {
            Locale.SIMPLIFIED_CHINESE,
            Locale.US
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FirstFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Locale locale = request.getLocale();
        LocalLocale.setLocale(getLocale(locale));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 获取支持语言
     *
     * @param locale
     * @return
     */
    private Locale getLocale(Locale locale) {
        String reqLanguage = locale.getLanguage();
        for (Locale support : supportLocales) {
            if (reqLanguage.equals(support.getLanguage())) {
                return support;
            }
        }
        return Locale.US;
    }


    @Override
    public void destroy() {
        System.out.println("FirstFilter destroy");
    }
}