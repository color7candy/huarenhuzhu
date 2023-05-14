package com.huzhu.controller;

import com.huzhu.common.WebUtils;
import com.huzhu.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.Cookie;

@Controller
@RequestMapping("/huzhu")
public class JumpController {


    @RequestMapping("/home")
    public String home() {
        return "huzhu/home";
    }

    @RequestMapping("/liveorfraud")
    public String liveorfraud() {
        return "huzhu/liveorfraud";
    }
    @RequestMapping("/fraud")
    public String fraud() {
        return "huzhu/fraud";
    }

    @RequestMapping("/report")
    public String report() {
        User user = (User) WebUtils.getSession().getAttribute("user");
        if (null != user){
            return "huzhu/report";
        }
        return "login/login";
    }

    @RequestMapping("/resort")
    public String resort() {
        return "huzhu/resort";
    }

    @RequestMapping("/user")
    public String user() {
        User user = (User) WebUtils.getSession().getAttribute("user");
        if (null != user){
            return "huzhu/user";
        }
        return "login/login";
    }

    @RequestMapping("/introduction")
    public String introduction() {
        return "huzhu/introduction";
    }

    @RequestMapping("/about")
    public String about() {
        return "huzhu/about";
    }

    @RequestMapping("/loginout")
    public String loginout() {
        Cookie cookie = new Cookie("user",null);
        cookie.setPath("/");
        WebUtils.getRespone().addCookie(cookie);
        WebUtils.getSession().setAttribute("user",null);
        return "huzhu/home";
    }

    @RequestMapping("/reset")
    public String reset() {
        return "huzhu/reset";
    }

    @RequestMapping("/login")
    public String login() {
        return "login/login";
    }

    @RequestMapping("/register")
    public String register() {
        return "login/register";
    }

    @RequestMapping("/forgot")
    public String forgot() {
        return "login/forgot";
    }

}
