package com.huzhu.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huzhu.entity.User;
import com.huzhu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setUserSession();
        return true;
        //        Object loginUser = request.getSession().getAttribute("huzhu");
//        if (loginUser == null) {
//            //未登录，返回登陆页
//            request.setAttribute("msg", "您没有权限进行此操作，请先登陆！");
//            request.getRequestDispatcher("/index.html").forward(request, response);
//            return false;
//        } else {
//            //放行
//        }
    }
    public void setUserSession() throws UnsupportedEncodingException {
        User user = (User) WebUtils.getSession().getAttribute("user");
        boolean isSet = false;
        if (user != null){
            isSet = isSetUser(user);
        }
        String userStr = WebUtils.getCookie("user");
        userStr = URLDecoder.decode(userStr,"utf-8");
        if (userStr.length() > 0){
            user = new User();
            String[] teamStr = userStr.split(",");
            for (int i = 0; i < teamStr.length; i++) {
                if (teamStr[i].length() > 0 && teamStr[i].contains("username:")){
                    String[] teamusername = teamStr[i].split(":");
                    if (teamusername.length >= 2 && teamusername[1].length() > 0){
                        user.setUsername(teamusername[1]);
                    }
                }
                if (teamStr[i].length() > 0 && teamStr[i].contains("password:")){
                    String[] teampassword = teamStr[i].split(":");
                    if (teampassword.length >= 2 && teampassword[1].length() > 0){
                        user.setPassword(teampassword[1]);
                    }
                }
            }
        }

        if (!isSet && user != null){
            isSet = isSetUser(user);
        }
        if (!isSet){
            WebUtils.getSession().setAttribute("user", null);
        }
    }
    public boolean isSetUser(User user){
        boolean isSet = false;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User sqluser = userService.getOne(queryWrapper);
        if (StringUtils.hasLength(user.getPassword()) && user.getPassword().equals(sqluser.getPassword())){
            isSet = true;
            WebUtils.getSession().setAttribute("user", sqluser);
        }
        return isSet;
    }
}
