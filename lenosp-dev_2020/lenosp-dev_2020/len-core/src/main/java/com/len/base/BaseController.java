package com.len.base;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.len.exception.ServiceException;
import com.len.util.LenResponse;
import com.len.util.MsHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email lenospmiller@gmail.com
 */
@Slf4j
public abstract class BaseController<T> {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
        if (isAjaxRequest(request)) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("code", "403");
            map.put("message", "无权限");
            return JSON.toJSONString(map);
        } else {
            String message = "权限不足";
            try {
                message = URLEncoder.encode(message, "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error("BaseController：" + e.getMessage());
                e.printStackTrace();
            }
            return "redirect:/error/403?message=" + message;
        }
    }

    @ExceptionHandler({Exception.class})
    public void runTimeException(Exception e, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            log.error(String.format("Unknown exception information :%s", e.getMessage()));
            LenResponse lenResponse = new LenResponse(false, MsHelper.getMsg("system.error"));
            response.getWriter().write(JSON.toJSONString(lenResponse));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @ExceptionHandler({ServiceException.class})
    public void serviceException(ServiceException e, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            log.error(String.format("serviceException :%s", e.getMessage()));
            String message = e.getMessage();
            LenResponse lenResponse = new LenResponse(false, message);
            response.getWriter().write(JSON.toJSONString(lenResponse));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        return requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest");
    }

    public LenResponse resp(boolean flag, String msg) {
        return new LenResponse(flag, msg);
    }

    public LenResponse resp(boolean flag) {
        return resp(flag, null);
    }

    public LenResponse succ(String msg) {
        return resp(true, msg);
    }

    public LenResponse succ() {
        return succ(null);
    }

    public LenResponse error(String msg) {
        return resp(false, msg);
    }

    public LenResponse error() {
        return error(null);
    }


}
