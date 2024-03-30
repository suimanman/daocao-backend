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

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("sys/menu")
@Slf4j
public class MenuController {

    @Resource
    IUmsMenuService menuService;

    @PutMapping
    public DaoCaoResult updateMenu(@Valid @RequestBody UmsMenu umsMenu){
        log.info("---------");
        int i = menuService.updateMenu(umsMenu);
        if(i>0){
            return DaoCaoResult.success();
        }else{
            return DaoCaoResult.error(HttpStatus.ERROR,"请检查菜单名称和地址是否已修改！");
        }

    }
    @DeleteMapping
    public DaoCaoResult deleteMenu(@RequestBody Long[] id){
        System.out.println("------>"+ Arrays.toString(id));
        int rows=menuService.removeMenu(id);
        if(rows>0){
            return DaoCaoResult.success();
        }else{
            return DaoCaoResult.error();
        }
    }
    @GetMapping("/{id}")
    public DaoCaoResult searchInfo(@PathVariable("id") Long id){
        UmsMenu umsMenu=menuService.searchInfo(id);
        return DaoCaoResult.success(umsMenu);
    }
    @GetMapping("/self")
    public DaoCaoResult searchSelfMenu(){
        //获取当前登录的用户ID，都在SecurityContextHolder中存储
        List<RouterVo> routerVoList=menuService.searchSelfMenu();
        return DaoCaoResult.success(routerVoList);
    }
    @GetMapping("/list")
    public DaoCaoResult searchMenuList(){
        //获取当前登录的用户ID，都在SecurityContextHolder中存储
        List<RouterVo> menuList=menuService.searchMenuList();
        return DaoCaoResult.success(menuList);
    }
    @PostMapping
    public DaoCaoResult submitMenuList(@Valid @RequestBody UmsMenu umsMenu){

        int i = menuService.saveMenu(umsMenu);
        if(i>0){
            return DaoCaoResult.success();
        }else{
            return DaoCaoResult.error(HttpStatus.ERROR,"新增菜单失败！,请检查是否有空值。");
        }

    }
}
