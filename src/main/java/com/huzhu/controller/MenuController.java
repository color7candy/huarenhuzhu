package com.huzhu.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huzhu.common.Constast;
import com.huzhu.common.DataGridView;
import com.huzhu.entity.Menu;
import com.huzhu.service.IMenuService;
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
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @RequestMapping("searchmenu")
    public DataGridView searchmenu(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        queryWrapper.orderByAsc("type");
        List<Menu> list = this.menuService.list(queryWrapper);
        return new DataGridView(list);
    }
}
