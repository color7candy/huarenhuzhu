package com.huzhu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huzhu.common.DataGridView;
import com.huzhu.common.ResultObj;
import com.huzhu.entity.Fraud;
import com.huzhu.entity.Resort;
import com.huzhu.entity.Star;
import com.huzhu.entity.User;
import com.huzhu.service.IFraudService;
import com.huzhu.service.IResortService;
import com.huzhu.service.IStarService;
import com.huzhu.service.IUserService;
import com.huzhu.vo.StarVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author user
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/star")
public class StarController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IStarService starService;
    @Autowired
    private IResortService resortService;
    @Autowired
    private IFraudService fraudService;
    @RequestMapping("changeStar")
    public ResultObj changeStar(  String username,
                                  String password,
                                  int typeid,
                                  String isadd,
                                  String isStar,
                                  String type,
                                  HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)) {
            boolean isSuccess = true;
            if ("1".equals(isadd)) {
                Star star = new Star();
                star.setIsstar(isStar);
                star.setUserid(sqluser.getId());
                star.setStarid(typeid);
                star.setType(type);
                isSuccess = starService.save(star);
                if (isSuccess){
                    if ("resort".equals(type)){
                        Resort resort = resortService.getById(typeid);
                        if ("1".equals(isStar)){
                            resort.setStar1count(""+(Integer.parseInt(resort.getStar1count())+1));
                        }
                        if ("2".equals(isStar)){
                            resort.setStar2count(""+(Integer.parseInt(resort.getStar2count())+1));
                        }
                        if ("3".equals(isStar)){
                            resort.setStar3count(""+(Integer.parseInt(resort.getStar3count())+1));
                        }
                    }else if ("fraud".equals(type)){
                        Fraud fraud = fraudService.getById(typeid);
                        if ("1".equals(isStar)){
                            fraud.setStar1count(""+(Integer.parseInt(fraud.getStar1count())+1));
                        }
                        if ("2".equals(isStar)){
                            fraud.setStar2count(""+(Integer.parseInt(fraud.getStar2count())+1));
                        }
                        if ("3".equals(isStar)){
                            fraud.setStar3count(""+(Integer.parseInt(fraud.getStar3count())+1));
                        }
                    }
                }
            }else if ("0".equals(isadd)){
                QueryWrapper<Star> starQueryWrapper = new QueryWrapper<>();
                starQueryWrapper.eq("starid",typeid);
                starQueryWrapper.eq("isstar",isStar);
                starQueryWrapper.eq("userid",sqluser.getId());
                isSuccess = starService.remove(starQueryWrapper);
                if (isSuccess){
                    if ("resort".equals(type)){
                        Resort resort = resortService.getById(typeid);
                        if ("1".equals(isStar)){
                            resort.setStar1count(""+(stringtoint(resort.getStar1count())-1));
                        }
                        if ("2".equals(isStar)){
                            resort.setStar2count(""+(stringtoint(resort.getStar2count())-1));
                        }
                        if ("3".equals(isStar)){
                            resort.setStar3count(""+(stringtoint(resort.getStar3count())-1));
                        }
                    } else if ("fraud".equals(type)) {
                        Fraud fraud = fraudService.getById(typeid);
                        if ("1".equals(isStar)){
                            fraud.setStar1count(""+(stringtoint(fraud.getStar1count())-1));
                        }
                        if ("2".equals(isStar)){
                            fraud.setStar2count(""+(stringtoint(fraud.getStar2count())-1));
                        }
                        if ("3".equals(isStar)){
                            fraud.setStar3count(""+(stringtoint(fraud.getStar3count())-1));
                        }
                    }
                }
            }
            if (isSuccess){
                return ResultObj.STAR_SUCCESS;
            }
        }
        return ResultObj.STAR_ERROR;
    }
    public int stringtoint(String str){
        if (str == null || str.length() <= 0){
            return 0;
        }else {
            return Integer.parseInt(str);
        }
    }

    @RequestMapping("searchstar1")
    public DataGridView searchstar1(StarVo starVo,
                                    String username,
                                    String password,
                                    HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            IPage<Star> page = new Page<>(starVo.getPage(), starVo.getLimit());
            QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid",sqluser.getId());
            queryWrapper.eq("isstar","1");
            this.starService.page(page, queryWrapper);
            return new DataGridView(page.getTotal(), page.getRecords());
        }
        return null;
    }
    @RequestMapping("searchstar2")
    public DataGridView searchstar2(StarVo starVo,
                                    String username,
                                    String password,
                                    HttpServletRequest request
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User sqluser = userService.getOne(userQueryWrapper);
        if (sqluser != null && sqluser.getPassword().equals(password)){
            IPage<Star> page = new Page<>(starVo.getPage(), starVo.getLimit());
            QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid",sqluser.getId());
            queryWrapper.eq("isstar","2");
            this.starService.page(page, queryWrapper);
            return new DataGridView(page.getTotal(), page.getRecords());
        }
        return null;
    }
}
