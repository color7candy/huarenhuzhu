package com.huzhu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huzhu.common.Constast;
import com.huzhu.common.DataGridView;
import com.huzhu.common.ResultObj;
import com.huzhu.common.WebUtils;
import com.huzhu.entity.Liveorfraud;
import com.huzhu.entity.Type;
import com.huzhu.entity.User;
import com.huzhu.service.ILiveorfraudService;
import com.huzhu.service.ITypeService;
import com.huzhu.service.IUserService;
import com.huzhu.vo.LiveorfraudVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author user
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/liveorfraud")
public class LiveorfraudController {
    @Autowired
    private ILiveorfraudService liveorfraudService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITypeService typeService;
    @RequestMapping("searchfraud")
    public DataGridView searchfraud(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<Liveorfraud> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constast.AVAILABLE_TRUE);
        queryWrapper.eq("liveorfraud","fraud");
        queryWrapper.orderByDesc("type");
        List<Liveorfraud> list = this.liveorfraudService.list(queryWrapper);
        return new DataGridView(list);
    }
    @RequestMapping("searchlive")
    public DataGridView searchlive(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<Liveorfraud> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constast.AVAILABLE_TRUE);
        queryWrapper.eq("liveorfraud","live");
        queryWrapper.orderByDesc("type");
        List<Liveorfraud> list = this.liveorfraudService.list(queryWrapper);
        return new DataGridView(list);
    }

    @RequestMapping("searchliveorfraudbyuser")
    public DataGridView searchliveorfraudbyuser(LiveorfraudVo liveorfraudVo,
                                                String username,
                                                String password,
                                                HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            IPage<Liveorfraud> page = new Page<>(liveorfraudVo.getPage(), liveorfraudVo.getLimit());
            QueryWrapper<Liveorfraud> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid",sqluser.getId());
            queryWrapper.orderByDesc("type");
            this.liveorfraudService.page(page, queryWrapper);
            return new DataGridView(page.getTotal(), page.getRecords());
        }
        return null;
    }

    @RequestMapping("liveorfrauddelete")
    public ResultObj liveorfrauddelete(String id,
                                       String username,
                                       String password,
                                       HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            Liveorfraud liveorfraud = this.liveorfraudService.getById(id);
            if (liveorfraud.getUserid() != sqluser.getId()){
                return ResultObj.DELETE_ERROR;
            }
            this.liveorfraudService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }
        return ResultObj.DELETE_ERROR;
    }
    @RequestMapping("liveorfraud")
    public ResultObj liveorfraud( String title,
                                  String liveorfraudType,
                                  String summernote1,
                                  String summernote2,
                                  String imagePathStr,
                                  HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        User user = (User) WebUtils.getSession().getAttribute("user");
        if (user.getVerified().equals("0")){
            return ResultObj.SAVE_ERROR;
        }
        Liveorfraud liveorfraud = new Liveorfraud();
        liveorfraud.setUserid(user.getId());
        liveorfraud.setTitle(title);
        if (liveorfraudType.length() <= 0){
            liveorfraudType = "";
        } else if (liveorfraudType.equals("请选择类型")) {
            liveorfraudType = "";
        }else {
            QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type",liveorfraudType);
            Type type = typeService.getOne(queryWrapper);
            if (type != null){
                liveorfraud.setLiveorfraud(type.getResortorfraud());
            }
        }
        liveorfraud.setType(liveorfraudType);
        liveorfraud.setDescription(summernote1);
        liveorfraud.setPrecaution(summernote2);
        liveorfraud.setStatus("1");
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
        liveorfraud.setImagearray(imagearray);
        liveorfraud.setImagepath(imagePathStr);
        if (this.liveorfraudService.save(liveorfraud)){
            return ResultObj.SAVE_SUCCESS;
        }else {
            return ResultObj.SAVE_ERROR;
        }
    }
}
