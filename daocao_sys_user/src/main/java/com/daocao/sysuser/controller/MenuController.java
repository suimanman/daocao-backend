package com.daocao.sysuser.controller;

import com.daocao.common.entity.UmsMenu;
import com.daocao.common.entity.vo.RouterVo;
import com.daocao.common.response.DaoCaoResult;
import com.daocao.common.service.IUmsMenuService;
import com.daocao.common.utils.security.DaoCaoSecurityUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sys/menu/")
public class MenuController {

    @Resource
    IUmsMenuService menuService;
    @GetMapping("self")
    public DaoCaoResult searchSelfMenu(){
        //获取当前登录的用户ID，都在SecurityContextHolder中存储
        List<RouterVo> routerVoList=menuService.searchSelfMenu();
        return DaoCaoResult.success(routerVoList);
    }
}
