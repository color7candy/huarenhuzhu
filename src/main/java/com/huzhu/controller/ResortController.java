package com.huzhu.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huzhu.common.*;
import com.huzhu.entity.*;
import com.huzhu.service.IContentService;
import com.huzhu.service.IResortService;
import com.huzhu.service.IStarService;
import com.huzhu.service.IUserService;
import com.huzhu.vo.ResortVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/resort")
public class ResortController {
    @Autowired
    private IResortService resortService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IStarService starService;
    @Autowired
    private IContentService contentService;

    @RequestMapping("searchresort")
    public DataGridView searchresort(ResortVo resortVo,
                                     String username,
                                     String password,
                                     String search,
                                     String area,
                                     String city,
                                     String type,
                                     String order,
                                     HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        if ("不限".equals(area)){area = "";}
        if ("不限".equals(city)){city = "";}
        if ("不限".equals(type)){type = "";}
        IPage<Resort> page = new Page<>(resortVo.getPage(), resortVo.getLimit());
        QueryWrapper<Resort> queryWrapper = new QueryWrapper<Resort>();
        queryWrapper.like(StringUtils.isNotBlank(search),"title",search)
                .or().like(StringUtils.isNotBlank(search),"resort_name",search)
                .or().like(StringUtils.isNotBlank(search),"resort_type",search)
                .or().like(StringUtils.isNotBlank(search),"description",search)
                .or().like(StringUtils.isNotBlank(search),"address",search);
        queryWrapper.eq("status", Constast.AVAILABLE_TRUE);
        queryWrapper.like(StringUtils.isNotBlank(area), "address", area);
        queryWrapper.like(StringUtils.isNotBlank(city), "address", city);
        queryWrapper.like(StringUtils.isNotBlank(type), "resort_type", type);
        if ("star1".equals(order)){
            queryWrapper.orderByDesc("star1count");
        }else if ("star2".equals(order)) {
            queryWrapper.orderByDesc("star2count");
        }else if ("content".equals(order)) {
            queryWrapper.orderByDesc("contentcount");
        }else {
            queryWrapper.orderByDesc("resort_time");
        }
        this.resortService.page(page, queryWrapper);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            QueryWrapper<Star> starQueryWrapper = new QueryWrapper<>();
            starQueryWrapper.eq("userid", sqluser.getId());
            starQueryWrapper.eq("type",Constast.RESORT);
            starQueryWrapper.eq("isstar","1");
            List<Star> list = starService.list(starQueryWrapper);
            for (Resort resort:page.getRecords()) {
                resort.setIsstar("0");
            }
            for (Resort resort:page.getRecords()) {
                for (Star star:list) {
                    if (star.getStarid().equals(resort.getId())){
                        resort.setIsstar("1");
                    }
                }

            }
        }
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @RequestMapping("searchresortbyuser")
    public DataGridView searchresortbyuser(ResortVo resortVo,
                                           String username,
                                           String password,
                                           HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            IPage<Resort> page = new Page<>(resortVo.getPage(), resortVo.getLimit());
            QueryWrapper<Resort> queryWrapper = new QueryWrapper<Resort>();
            queryWrapper.eq("userid",sqluser.getId());
            queryWrapper.orderByDesc("resort_time");
            this.resortService.page(page, queryWrapper);
            return new DataGridView(page.getTotal(), page.getRecords());
        }
        return null;
    }
    @RequestMapping("searchresortbystarid")
    public DataGridView searchresortbystarid(String id,
                                             HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        return new DataGridView(this.resortService.getById(id));
    }
    @RequestMapping("resortdelete")
    public ResultObj resortdelete(String typeid,
                                  String username,
                                  String password,
                                  HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            Resort resort = this.resortService.getById(typeid);
            if (resort.getUserid() != sqluser.getId()){
                return ResultObj.DELETE_ERROR;
            }
            this.resortService.removeById(typeid);
            QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid", sqluser.getId());
            queryWrapper.eq("type",Constast.RESORT);
            queryWrapper.eq("starid",typeid);
            List<Star> starlist = this.starService.list(queryWrapper);
            for (int i = 0; i < starlist.size(); i++) {
                Star star = (Star) starlist.get(i);
                this.starService.removeById(star.getId());
            }
            QueryWrapper<Content> contentWrapper = new QueryWrapper<>();
            contentWrapper.eq("userid", sqluser.getId());
            contentWrapper.eq("content_type",Constast.RESORT);
            contentWrapper.eq("contentid",typeid);
            List<Content> contentlist = this.contentService.list(contentWrapper);
            for (int i = 0; i < contentlist.size(); i++) {
                Content content = (Content) contentlist.get(i);
                this.contentService.removeById(content.getId());
            }
            return ResultObj.DELETE_SUCCESS;
        }
        return ResultObj.DELETE_ERROR;
    }

    @RequestMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        //1,得到文件名
        String oldName = file.getOriginalFilename();
        //2,根据文件名生成新的文件名
        String newName = AppFileUtils.createNewFileName(oldName);
        //3,得到当前日期的字符串
        String dirName = DateUtil.format(new Date(), "yyyy-MM-dd");
        //4,构造文件夹
        File dirFile = new File(AppFileUtils.UPLOAD_PATH, dirName);
        //5,判断当前文件夹是否存在
        if (!dirFile.exists()) {
            dirFile.mkdirs();//创建文件夹
        }
        //6,构造文件对象
        File file1 = new File(dirFile, newName + "");
        ObjectMapper mapper = new ObjectMapper();
        //7,把mf里面的图片信息写入file
        try {
            file.transferTo(file1);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            file1.delete();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("isError","true");
            String json = mapper.writeValueAsString(map);
            return json;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("path", "http://www.huarenhuzhu.com/resources/" + dirName+"/" + newName);
        map.put("isError","false");
        String json = mapper.writeValueAsString(map);
        return json;
    }

    @RequestMapping("resort")
    public ResultObj resort(      String title,
                                  String resortName,
                                  String resortType,
                                  String area,
                                  String city,
                                  String contact,
                                  String summernote,
                                  String imagePathStr,
                                  HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        User user = (User) WebUtils.getSession().getAttribute("user");
        if (user.getVerified().equals("0")){
            return ResultObj.SAVE_ERROR;
        }
        Resort resort = new Resort();
        resort.setUserid(user.getId());
        resort.setTitle(title);
        if (resortName.length() <= 0){
            resortName = "";
        }
        resort.setResortName(resortName);
        if (resortType.length() <= 0){
            resortType = "";
        } else if (resortType.equals("请选择类型")) {
            resortType = "";
        }
        resort.setResortType(resortType);
        resort.setResortTime(LocalDateTime.now());
        resort.setDescription(summernote);
        if (area.length() <= 0){
            area = "";
        } else if (area.equals("请选择区域")) {
            area = "";
        }
        if (city.length() <= 0){
            city = "";
        }else if (city.equals("请选择城市")){
            city = "";
        }
        if (contact.length() <= 0){
            contact = "";
        }
        resort.setStatus("1");
        resort.setAddress(area + " " +city);
        resort.setContact(contact);
        String imagearray = "";
        if (imagePathStr.length() <= 0){
            imagePathStr = "";
        }else {
            String[] str = imagePathStr.split(",");
            imagePathStr = str[0];
            for (int i = 1; i < str.length; i++) {
                if (imagearray.length()<=0){
                    imagearray=str[i]+",";
                }else {
                    imagearray=imagearray+str[i]+",";
                }
            }
        }
        resort.setImagepath(imagePathStr);
        resort.setImagearray(imagearray);
        resort.setContentcount("0");
        resort.setStar1count("0");
        resort.setStar2count("0");
        resort.setStar3count("0");
        if (this.resortService.save(resort)){
            return ResultObj.SAVE_SUCCESS;
        }else {
            return ResultObj.SAVE_ERROR;
        }
    }

    @RequestMapping("selectresort")
    public ModelAndView selectresort(String id,
                                     HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        Resort resort = this.resortService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/huzhu/resortinfo");
        modelAndView.addObject("resort",resort);
        return modelAndView;
    }

    @RequestMapping("searchuser")
    public DataGridView searchuser(String userid,
                                   HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        User user = this.userService.getById(userid);
        return new DataGridView(user);
    }
    @RequestMapping("searchstar")
    public DataGridView searchstar(String id,
                                   HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<Star> starQueryWrapper = new QueryWrapper<>();
        starQueryWrapper.eq("type", "resort");
        starQueryWrapper.eq("starid", id);
        List<Star> starList = this.starService.list(starQueryWrapper);
        return new DataGridView(starList);
    }
    @RequestMapping("searchcontent")
    public DataGridView searchcontent(String id,
                                      HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<Content> contentQueryWrapper = new QueryWrapper<>();
        contentQueryWrapper.eq("content_type", "resort");
        contentQueryWrapper.eq("contentid", id);
        List<Content> contentList = this.contentService.list(contentQueryWrapper);
        return new DataGridView(contentList);
    }
    @RequestMapping("searchcontentcontent")
    public DataGridView searchcontentcontent(String id,
                                             HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<Content> contentQueryWrapper = new QueryWrapper<>();
        contentQueryWrapper.eq("content_type", "content");
        contentQueryWrapper.eq("contentid", id);
        List<Content> contentList = this.contentService.list(contentQueryWrapper);
        return new DataGridView(contentList);
    }

}
