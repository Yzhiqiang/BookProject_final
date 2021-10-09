package com.len.core.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author zhuxiaomeng
 * @date 2017/12/29.
 * @email lenospmiller@gmail.com
 * <p>
 * 验证码拦截
 */
public class VerfityCodeFilter extends AccessControlFilter {
    /**
     * 是否开启验证码验证   默认true
     */
    private boolean verfitiCode = true;

    /**
     * 前台提交的验证码name
     */
    private String jcaptchaParam = "code";

    /**
     * 验证失败后setAttribute key
     */
    private String failureKeyAttribute = "shiroLoginFailure";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        request.setAttribute("verfitiCode", verfitiCode);//暂时未用到非验证码
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //2、判断验证码是否禁用 或不是表单提交
        if (!verfitiCode || !"post".equalsIgnoreCase(httpRequest.getMethod())) {
            return true;
        }
        Session session = getSubject(request, response).getSession();
        Collection<Object> attributeKeys = session.getAttributeKeys();
        Object code = getSubject(request, response).getSession().getAttribute(jcaptchaParam);
        String storedCode = null;
        if (null != code) {
            storedCode = code.toString();
        } else {
            request.setAttribute(failureKeyAttribute, "code.timeout");
            return false;
        }
        //表单提交，校验验证码的正确性
        String currentCode = httpRequest.getParameter(jcaptchaParam);

        return StringUtils.equalsIgnoreCase(storedCode, currentCode);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if (servletRequest.getAttribute(failureKeyAttribute) == null) {
            servletRequest.setAttribute(failureKeyAttribute, "code.error");
        }
        return true;
    }

    public boolean isVerfitiCode() {
        return verfitiCode;
    }

    public void setVerfitiCode(boolean verfitiCode) {
        this.verfitiCode = verfitiCode;
    }

    public String getJcaptchaParam() {
        return jcaptchaParam;
    }

    public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }

    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }
}
