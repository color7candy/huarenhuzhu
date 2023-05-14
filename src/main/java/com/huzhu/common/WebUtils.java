package com.huzhu.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtils {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    public static HttpServletResponse getRespone() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        return response;
    }


    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getCookie(String name) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null){
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

}
