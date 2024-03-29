package com.daocao.sysuser.controller;

import com.daocao.common.constants.HttpStatus;
import com.daocao.common.entity.UmsMenu;
import com.daocao.common.entity.vo.RouterVo;
import com.daocao.common.response.DaoCaoResult;
import com.daocao.common.service.IUmsMenuService;
import com.daocao.common.utils.security.DaoCaoSecurityUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/menu/")
@Slf4j
public class MenuController {

    @Resource
    IUmsMenuService menuService;
    @GetMapping("self")
    public DaoCaoResult searchSelfMenu(){
        //获取当前登录的用户ID，都在SecurityContextHolder中存储
        List<RouterVo> routerVoList=menuService.searchSelfMenu();
        return DaoCaoResult.success(routerVoList);
    }
    @GetMapping("list")
    public DaoCaoResult searchMenuList(){
        //获取当前登录的用户ID，都在SecurityContextHolder中存储
        List<RouterVo> menuList=menuService.searchMenuList();
        return DaoCaoResult.success(menuList);
    }
    @PostMapping("list")
    public DaoCaoResult submitMenuList(@Valid @RequestBody UmsMenu umsMenu){

        int i = menuService.saveMenu(umsMenu);
        if(i>0){
            return DaoCaoResult.success();
        }else{
            return DaoCaoResult.error(HttpStatus.ERROR,"新增菜单失败！,请检查是否有空值。");
        }

    }
}
