package com.huzhu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huzhu.common.Constast;
import com.huzhu.common.DataGridView;
import com.huzhu.common.ResultObj;
import com.huzhu.entity.Content;
import com.huzhu.entity.User;
import com.huzhu.service.IContentService;
import com.huzhu.service.IFraudService;
import com.huzhu.service.IResortService;
import com.huzhu.service.IUserService;
import com.huzhu.vo.ContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author user
 * @since 2023-04-05
 */
@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private IContentService contentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFraudService fraudService;
    @Autowired
    private IResortService resortService;

    @RequestMapping("searchsystem")
    public DataGridView searchsystem(ContentVo contentVo,
                                     String username,
                                     String password,
                                     HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            IPage<Content> page = new Page<>(contentVo.getPage(), contentVo.getLimit());
            QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("contentid",sqluser.getId());
            queryWrapper.eq("content_type","system");
            queryWrapper.orderByDesc("content_time");
            this.contentService.page(page, queryWrapper);
            return new DataGridView(page.getTotal(), page.getRecords());
        }
        return null;
    }
    @RequestMapping("searchprivate")
    public DataGridView searchprivate(ContentVo contentVo,
                                      String username,
                                      String password,
                                      HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            IPage<Content> page = new Page<>(contentVo.getPage(), contentVo.getLimit());
            QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("contentid",sqluser.getId())
                    .or().eq("userid",sqluser.getId());
            queryWrapper.eq("content_type","private");
            queryWrapper.orderByDesc("content_time");
            this.contentService.page(page, queryWrapper);
            return new DataGridView(page.getTotal(), page.getRecords());
        }
        return null;
    }
    @RequestMapping("searchcontent")
    public DataGridView searchcontent(ContentVo contentVo,
                                      String username,
                                      String password,
                                      HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            IPage<Content> page = new Page<>(contentVo.getPage(), contentVo.getLimit());
            QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("contentid",sqluser.getId())
                    .or().eq("userid",sqluser.getId());
            queryWrapper.eq("content_type", Constast.FRAUD)
                    .or().eq("content_type",Constast.RESORT)
                    .or().eq("content_type","content");
            queryWrapper.orderByDesc("content_time");
            this.contentService.page(page, queryWrapper);
            return new DataGridView(page.getTotal(), page.getRecords());
        }
        return null;
    }

    @RequestMapping("searchfraudorresort")
    public DataGridView searchfraudorresort(String index,
                                      HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        return new DataGridView(searchC(index));
    }
    public Content searchC (String index){
        Content content = this.contentService.getById(index);
        if (content.getContentType().equals("content")){
            return searchC(""+content.getContentid());
        }else {
            return content;
        }
    }
    @RequestMapping("privatedelete")
    public ResultObj privatedelete(String typeid,
                                   String username,
                                   String password,
                                   HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            Content content = this.contentService.getById(typeid);
            if (content.getContentType().equals("private") &&
                    content.getUserid().equals(sqluser.getId()) ||
                    content.getContentid().equals(sqluser.getId())){
                this.contentService.removeById(typeid);
                return ResultObj.DELETE_SUCCESS;
            }
        }
        return ResultObj.DELETE_ERROR;
    }
    @RequestMapping("commit")
    public ResultObj commit(        String id,
                                    String userid,
                                    String content,
                                    String contenttype,
                                    String image,
                                    HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        Content content1 = new Content();
        content1.setContentid(Integer.parseInt(id));
        content1.setContent(content);
        content1.setContentTime(LocalDateTime.now());
        String imagearray = "";
        if (image.length() <= 0){
            image = "";
        }else {
            String[] str = image.split(",");
            image = str[0];
            for (int i = 1; i < str.length; i++) {
                if (imagearray.length() <= 0){
                    imagearray=str[i]+",";
                }else {
                    imagearray=imagearray+str[i]+",";
                }
            }
        }
        content1.setContentImage(image);
        content1.setContentImagearray(imagearray);
        content1.setContentType(contenttype);
        content1.setUserid(Integer.parseInt(userid));
        if (this.contentService.save(content1)){
            return ResultObj.SAVE_SUCCESS;
        }else {
            return ResultObj.SAVE_ERROR;
        }

    }
    @RequestMapping("contentdelete")
    public ResultObj contentdelete(String typeid,
                                   String username,
                                   String password,
                                   HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            Content content = this.contentService.getById(typeid);
            if (content.getUserid().equals(sqluser.getId()) ||
                    content.getContentid().equals(sqluser.getId())){
                this.contentService.removeById(typeid);
                return ResultObj.DELETE_SUCCESS;
            }
        }
        return ResultObj.DELETE_ERROR;
    }
    @RequestMapping("searchcontentbyidandtype")
    public DataGridView searchcontentbyidandtype(String id,
                                                 String type,
                                                 HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        if (type.equals(Constast.FRAUD)){
            return new DataGridView(this.fraudService.getById(id));
        }else if(type.equals(Constast.RESORT)){
            return new DataGridView(this.resortService.getById(id));
        }else {
            return new DataGridView(searchfraudorresort(id));
        }
    }

    public Object searchfraudorresort(String id){
        Content content = this.contentService.getById(id);
        if (content.getContentType().equals("content")){
            return searchfraudorresort(""+content.getContentid());
        }else if (content.getContentType().equals(Constast.FRAUD)){
            return this.fraudService.getById(id);
        }else{
            return this.resortService.getById(id);
        }
    }
}
