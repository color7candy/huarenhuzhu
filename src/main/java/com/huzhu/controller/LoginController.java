package com.huzhu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huzhu.common.MD5Utils;
import com.huzhu.common.ResultObj;
import com.huzhu.common.WebUtils;
import com.huzhu.entity.Logininfo;
import com.huzhu.entity.User;
import com.huzhu.service.ILogininfoService;
import com.huzhu.service.IUserService;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUserService userService;
//    @Autowired
//    private ILogininfoService logininfoService;
    @Autowired//自动装配
    JavaMailSender mailSender;
    @RequestMapping("/getVerifiCode")
    public void getVerifiCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        CaptchaUtil.out(request,response);
    }
    @RequestMapping("/checkyzm")
    public ResultObj checkyzm( String yzm,HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        Boolean isSuccess =CaptchaUtil.ver(yzm, request);
        if(isSuccess) {
            return ResultObj.YZM_SUCCESS;
        }
        return ResultObj.YZM_ERROR;
    }
    @RequestMapping("/forgot")
    public ResultObj forgot(String email, String yzm,HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        Boolean isSuccess =CaptchaUtil.ver(yzm, request);
        if(isSuccess) {
            CaptchaUtil.clear(request);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", email);
            User user = userService.getOne(queryWrapper);
            if (user != null){
                new Thread(){
                    public void run() {
                        try {
                            sendEmail(user);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }.start();
                return ResultObj.SEND_SUCCESS;
            }
            return ResultObj.SEND_ERROR_CODE;
        }
        return ResultObj.SEND_ERROR_PASS;
    }
    public void sendEmail(User user) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setSubject("[华人互助网]找回密码");
        mimeMessageHelper.setText("<h3>尊敬的用户</h3>\n" + "\n" + "<label>您好！您收到此邮件是因为您请求找回密码。以下是您的账户信息：</label>\n" + "<br/>\n" + "<label>用户名：" +user.getUsername() + "</label>\n" + "<br/>\n" + "<label>如果您不是此次请求的发起者，请忽略此邮件。</label>\n" + "<br/>\n" + "<label>若您是发起者，您可以点击以下链接，按照提示重置您的密码：</label>\n" + "<br/><a href=\"http://www.huarenhuzhu.com/login/forgotpwd?email=" + user.getEmail() + "&username=" +user.getUsername()+"&hash="+user.getSalt()+ "\">点击此处找回密码</a><br/>\n" + "<label>如有其他问题，请联系我们的客服团队。</label>\n" + "<br/>\n" + "<label>祝您使用愉快！</label>\n" + "<br/>\n" + "<label>此致，</label>\n" + "<br/>\n" + "<label>华人互助网</label>",true);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setFrom("华人互助网<3351902681@qq.com>");
        mailSender.send(mimeMessage);
    }
    @RequestMapping("/forgotpwd")
    public ModelAndView forgotpwd(String email , String username ,String hash,HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        queryWrapper.eq("username", username);
        queryWrapper.eq("salt",hash);
        User user = userService.getOne(queryWrapper);
        if (user != null){
            WebUtils.getSession().setAttribute("username", user.getUsername());
            return new ModelAndView("login/forgotpwd");
        }else {
            return new ModelAndView("huzhu/home");
        }
    }
    @RequestMapping(value = "/setpassword")
    public ResultObj setpassword(String username , String newpwd,HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userService.getOne(queryWrapper);
        if (user != null){
            user.setPassword(MD5Utils.md5(newpwd,user.getSalt()));
            userService.updateById(user);
            WebUtils.getSession().setAttribute("user", user);
           return ResultObj.RESET_SUCCESS;
        }else {
            return ResultObj.RESET_ERROR;
        }
    }

    @RequestMapping(value = "/login")
    public ResultObj login(String username, String password, String autoLogin, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User user = userService.getOne(queryWrapper);
            if (null == user){
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("email", username);
                user = userService.getOne(queryWrapper);
            }
            if (null != user){
                if(MD5Utils.verify(password,user.getSalt(),user.getPassword())){
                    WebUtils.getSession().setAttribute("user", user);
                    if (autoLogin != null && autoLogin.equalsIgnoreCase("on")) {
                        String userStr ="username:"+user.getUsername()+",password:"+user.getPassword()+"";
                        userStr = URLEncoder.encode(userStr,"utf-8");
                        Cookie cookie = new Cookie("user", userStr);
                        cookie.setPath("/");
                        WebUtils.getRespone().addCookie(cookie);
                    }
                    //记录登陆日志
//                    Logininfo entity = new Logininfo();
//                    entity.setUserid(user.getId());
//                    entity.setNickname(user.getNickname());
//                    entity.setLoginip(WebUtils.getRequest().getRemoteAddr());
//                    entity.setLogintime(LocalDateTime.now());
//                    logininfoService.save(entity);
                    return ResultObj.LOGIN_SUCCESS;
                }
            }
            return ResultObj.LOGIN_ERROR_PASS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.LOGIN_ERROR_PASS;
        }
    }
    @RequestMapping("/searchuser")
    public ResultObj searchuser(String username, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User user = userService.getOne(queryWrapper);
            if (null != user){
                return ResultObj.SEARCH_ERROR;
            }
            return ResultObj.SEARCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SEARCH_SUCCESS;
        }
    }

    @RequestMapping("/searchemail")
    public ResultObj searchemail(String email, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email", email);
            User user = userService.getOne(queryWrapper);
            if (null != user){
                return ResultObj.SEARCH_ERROR;
            }
            return ResultObj.SEARCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SEARCH_SUCCESS;
        }

    }

    @RequestMapping("/register")
    public ResultObj saveUser(String username,
                              String oldpwd,
                              String email,
                              String yzm,
                              HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        try {
            Boolean isSuccess =CaptchaUtil.ver(yzm, request);
            if (isSuccess){
                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                String uuid = UUID.randomUUID().toString();
                uuid = uuid.replace("-", "");
                user.setSalt(uuid);
                user.setPassword(MD5Utils.md5(oldpwd,uuid));
                user.setVerified("0");
                user.setLimits("0");
                user.setNickname("");
                user.setImagepath("");
                user.setContact("");
                user.setAddress("");
                user.setRegTime(LocalDateTime.now());
                if(userService.save(user)){
                    new Thread(){
                        public void run() {
                            try {
                                confirmEmail(user);
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }.start();
                    WebUtils.getSession().setAttribute("user", user);
                    return ResultObj.USERSAVE_SUCCESS;
                }
                return ResultObj.USERSAVE_ERROR_PASS;
            }
            return ResultObj.YZM_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.USERSAVE_ERROR_CODE;
        }
    }
    public void confirmEmail(User user) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setSubject("[华人互助网]验证邮箱");
        mimeMessageHelper.setText("<h3>尊敬的：" + user.getUsername() +
                "，您好：</h3>\n" + "<label>感谢您在 华人互助网 注册账号。为了确保您的账户安全，我们需要验证您的电子邮件。</label>\n" + "<br/>\n" + "<label>如果您不是此次请求的发起者，请忽略此邮件。</label>\n" + "<br/>\n" + "<label>若您是发起者，您可以点击以下链接，完成验证</label>\n" + "<br/><a href=\"http://www.huarenhuzhu.com/login/emailcheck?hash=" + user.getSalt() + "&username="+ user.getUsername() + "\">点击此处验证email</a>\n" + "<br/>\n"+ "<label>如有其他问题，请联系我们的客服团队。</label>\n" + "<br/>\n" + "<label>祝您使用愉快！</label>\n" + "<br/>\n" + "<label>此致，</label>\n" + "<br/>\n" + "<label>华人互助网</label>",true);

        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setFrom("华人互助网<3351902681@qq.com>");

        mailSender.send(mimeMessage);
    }

    @RequestMapping("/emailcheck")
    public ModelAndView emailcheck(String hash ,String username, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            queryWrapper.eq("salt",hash);
            User user = userService.getOne(queryWrapper);
            if (null != user){
                user.setVerified("1");
                userService.updateById(user);
                WebUtils.getSession().setAttribute("user", user);
            }
            return new ModelAndView("huzhu/emailcheck");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("huzhu/emailcheck");
        }

    }

}