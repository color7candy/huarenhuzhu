package com.huzhu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huzhu.common.Constast;
import com.huzhu.common.DataGridView;
import com.huzhu.common.ResultObj;
import com.huzhu.common.WebUtils;
import com.huzhu.entity.*;
import com.huzhu.service.IContentService;
import com.huzhu.service.IFraudService;
import com.huzhu.service.IStarService;
import com.huzhu.service.IUserService;
import com.huzhu.vo.FraudVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author user
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/fraud")
public class FraudController {
    @Autowired
    private IFraudService fraudService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IStarService starService;
    @Autowired
    private IContentService contentService;
    @RequestMapping("searchfraud")
    public DataGridView searchfraud(FraudVo fraudVo,
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
        IPage<Fraud> page = new Page<>(fraudVo.getPage(), fraudVo.getLimit());
        QueryWrapper<Fraud> queryWrapper = new QueryWrapper<Fraud>();
        queryWrapper.like(StringUtils.isNotBlank(search),"title",search)
                .or().like(StringUtils.isNotBlank(search),"fraud_name",search)
                .or().like(StringUtils.isNotBlank(search),"fraud_type",search)
                .or().like(StringUtils.isNotBlank(search),"description",search)
                .or().like(StringUtils.isNotBlank(search),"address",search)
                .or().like(StringUtils.isNotBlank(search),"contact",search);
        queryWrapper.eq("status", Constast.AVAILABLE_TRUE);
        queryWrapper.like(StringUtils.isNotBlank(area), "address", area);
        queryWrapper.like(StringUtils.isNotBlank(city), "address", city);
        queryWrapper.like(StringUtils.isNotBlank(type), "fraud_type", type);
        if ("star2".equals(order)) {
            queryWrapper.orderByDesc("star2count");
        }else if ("content".equals(order)) {
            queryWrapper.orderByDesc("contentcount");
        }else {
            queryWrapper.orderByDesc("fraud_time");
        }
        this.fraudService.page(page, queryWrapper);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            QueryWrapper<Star> starQueryWrapper = new QueryWrapper<>();
            starQueryWrapper.eq("userid", sqluser.getId());
            starQueryWrapper.eq("type",Constast.FRAUD);
            starQueryWrapper.eq("isstar","2");
            List<Star> list = starService.list(starQueryWrapper);
            for (Fraud fraud:page.getRecords()) {
                fraud.setIsstar("0");
            }
            for (Fraud fraud:page.getRecords()) {
                for (Star star:list) {
                    if (star.getStarid().equals(fraud.getId())){
                        fraud.setIsstar("1");
                    }
                }

            }
        }
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @RequestMapping("searchfraudbyuser")
    public DataGridView searchfraudbyuser(FraudVo fraudVo,
                                          String username,
                                          String password,
                                          HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            IPage<Fraud> page = new Page<>(fraudVo.getPage(), fraudVo.getLimit());
            QueryWrapper<Fraud> queryWrapper = new QueryWrapper<Fraud>();
            queryWrapper.eq("userid",sqluser.getId());
            queryWrapper.orderByDesc("fraud_time");
            this.fraudService.page(page, queryWrapper);
            return new DataGridView(page.getTotal(), page.getRecords());
        }
        return null;
    }
    @RequestMapping("searchfraudbystarid")
    public DataGridView searchfraudbystarid(String id,
                                            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        return new DataGridView( this.fraudService.getById(id));

    }

    @RequestMapping("frauddelete")
    public ResultObj frauddelete(String typeid,
                                 String username,
                                 String password,
                                 HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            Fraud fraud = this.fraudService.getById(typeid);
            if (fraud.getUserid() != sqluser.getId()){
                return ResultObj.DELETE_ERROR;
            }
            this.fraudService.removeById(typeid);
            QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid", sqluser.getId());
            queryWrapper.eq("type",Constast.FRAUD);
            queryWrapper.eq("starid",typeid);
            List<Star> starlist = this.starService.list(queryWrapper);
            for (int i = 0; i < starlist.size(); i++) {
                Star star = (Star) starlist.get(i);
                this.starService.removeById(star.getId());
            }
            QueryWrapper<Content> contentWrapper = new QueryWrapper<>();
            contentWrapper.eq("userid", sqluser.getId());
            contentWrapper.eq("content_type",Constast.FRAUD);
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

    @RequestMapping("fraud")
    public ResultObj fraud(      String title,
                                 String fraudName,
                                 String fraudType,
                                 String area,
                                 String city,
                                 String contact,
                                 String amount,
                                 String fraudMethod,
                                 String summernote,
                                 String imagePathStr,
                                 HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        User user = (User) WebUtils.getSession().getAttribute("user");
        if (user.getVerified().equals("0")){
            return ResultObj.SAVE_ERROR;
        }
        Fraud fraud = new Fraud();
        fraud.setUserid(user.getId());
        fraud.setTitle(title);
        if (fraudName.length() <= 0){
            fraudName = "";
        }
        fraud.setFraudName(fraudName);
        if (fraudType.length() <= 0){
            fraudType = "";
        } else if (fraudType.equals("请选择类型")) {
            fraudType = "";
        }
        if (amount.length() <= 0){
            amount = "0";
        }
        BigDecimal bigDecimal = new BigDecimal(amount);
        if (fraudMethod.length() <= 0){
            fraudMethod = "";
        }
        fraud.setFraudMethod(fraudMethod);
        fraud.setAmount(bigDecimal);
        fraud.setFraudType(fraudType);
        fraud.setFraudTime(LocalDateTime.now());
        fraud.setDescription(summernote);
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
        fraud.setStatus("1");
        fraud.setAddress(area + " " +city);
        fraud.setContact(contact);
        String imagearray = "";
        if (imagePathStr.length() <= 0){
            imagePathStr = "";
        }else {
            String[] str = imagePathStr.split(",");
            imagePathStr = str[0];
            for (int i = 1; i < str.length; i++) {
                if (imagearray.length() <= 0){
                    imagearray=str[i]+",";
                }else {
                    imagearray=imagearray+str[i]+",";
                }
            }
        }
        fraud.setImagepath(imagePathStr);
        fraud.setImagearray(imagearray);
        fraud.setContentcount("0");
        fraud.setStar1count("0");
        fraud.setStar2count("0");
        fraud.setStar3count("0");
        if (this.fraudService.save(fraud)){
            return ResultObj.SAVE_SUCCESS;
        }else {
            return ResultObj.SAVE_ERROR;
        }
    }
    @RequestMapping("selectfraud")
    public ModelAndView selectfraud(String id,
                                    HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        Fraud fraud = this.fraudService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/huzhu/fraudinfo");
        modelAndView.addObject("fraud",fraud);
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
        starQueryWrapper.eq("type", "fraud");
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
        contentQueryWrapper.eq("content_type", "fraud");
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
