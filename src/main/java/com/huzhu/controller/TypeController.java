package com.huzhu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huzhu.common.Constast;
import com.huzhu.common.DataGridView;
import com.huzhu.entity.Type;
import com.huzhu.service.ITypeService;
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
 * @since 2023-04-05
 */
@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private ITypeService typeService;
    @RequestMapping("searchresorttype")
    public DataGridView searchresorttype(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        queryWrapper.eq("resortorfraud",Constast.RESORT);
        List<Type> list = this.typeService.list(queryWrapper);
        return new DataGridView(list);
    }
    @RequestMapping("searchfraudtype")
    public DataGridView searchfraudtype(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        queryWrapper.eq("resortorfraud",Constast.FRAUD);
        List<Type> list = this.typeService.list(queryWrapper);
        return new DataGridView(list);
    }
    @RequestMapping("searchliveorfraudtype")
    public DataGridView searchliveorfraudtype(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        queryWrapper.eq("resortorfraud",Constast.LIVE)
                .or().eq("resortorfraud",Constast.FRAUD);
        queryWrapper.orderByDesc("resortorfraud");
        List<Type> list = this.typeService.list(queryWrapper);
        return new DataGridView(list);
    }
}
