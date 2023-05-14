package com.huzhu.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huzhu.common.*;
import com.huzhu.entity.User;
import com.huzhu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author user
 * @since 2023-04-05
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @RequestMapping(value = "/reset")
    public ResultObj setpassword(String username, String oldpwd , String newpwd, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User user = userService.getOne(queryWrapper);
            if (null != user){
                if(MD5Utils.verify(oldpwd,user.getSalt(),user.getPassword())){
                    user.setPassword(MD5Utils.md5(newpwd,user.getSalt()));
                    if(userService.updateById(user)){
                        WebUtils.getSession().setAttribute("user", user);
                        return ResultObj.PWDCHANGE_SUCCESS;
                    }
                }
            }
            return ResultObj.PWDCHANGE_ERROR_PASS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.PWDCHANGE_ERROR_CODE;
        }
    }

    @RequestMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        String oldName = file.getOriginalFilename();
        //2,根据文件名生成新的文件名
        String newName = AppFileUtils.createNewFileName(oldName);
        //3,得到当前日期的字符串
        String dirName = DateUtil.format(new Date(), "yyyy-MM-dd");
        //4,构造文件夹
        File dirFile = new File(AppFileUtils.UPLOAD_PATH, dirName);
        ObjectMapper mapper = new ObjectMapper();
        //5,判断当前文件夹是否存在
        if (!dirFile.exists()) {
            dirFile.mkdirs();//创建文件夹
        }
        //6,构造文件对象
        File file1 = new File(dirFile, newName + "");
        //7,把mf里面的图片信息写入file
        try {
            file.transferTo(file1);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("isError","true");
            file1.delete();
            // 将 Map 对象转换为 JSON 字符串
            String json = mapper.writeValueAsString(map);
            return json;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("path", "http://www.huarenhuzhu.com/resources/" + dirName+"/" + newName);
        Object object = WebUtils.getSession().getAttribute("user");
        if (object instanceof User){
            User user = (User) object;
            user.setImagepath("http://www.huarenhuzhu.com/resources/" + dirName+"/" + newName);
            if(userService.updateById(user)){
                WebUtils.getSession().setAttribute("user", user);
                map.put("isError","false");
                String json = mapper.writeValueAsString(map);
                return json;
            }
        }
        file1.delete();
        map.put("isError","true");
        String json = mapper.writeValueAsString(map);
        return json;
    }

    @RequestMapping(value = "/changeuser")
    public ResultObj changeuser(String username, String password , String imagepath,String nickname,String contact,String area,String city, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User user = userService.getOne(queryWrapper);
            if (null != user){
                if(user.getPassword().equals(password)){
                    user.setImagepath(imagepath);
                    user.setNickname(nickname);
                    user.setContact(contact);
                    String address = "";
                    if (!area.equals("不限") && !city.equals("不限")){
                        address = area+"-"+city;
                    }else if (!area.equals("不限")){
                        address = area;
                    }
                    user.setAddress(address);
                    if(userService.updateById(user)){
                        WebUtils.getSession().setAttribute("user", user);
                        return ResultObj.USERCHANGE_SUCCESS;
                    }
                }
            }
            return ResultObj.USERCHANGE_ERROR_CODE;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.USERCHANGE_ERROR_CODE;
        }
    }
    @RequestMapping("searchuserbyid")
    public DataGridView searchuserbyid(String userid,
                                       HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        return new DataGridView(this.userService.getById(userid));
    }
}
