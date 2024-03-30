package com.daocao.sysuser.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daocao.common.constants.HttpStatus;
import com.daocao.common.entity.UmsMenu;
import com.daocao.common.entity.vo.RouterVo;
import com.daocao.common.response.DaoCaoPageResult;
import com.daocao.common.response.DaoCaoResult;
import com.daocao.common.service.IUmsMenuService;
import com.daocao.sysuser.domain.dto.UmsMenuParamDto;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("sys/menu")
@Slf4j
public class MenuController {

    @Resource
    IUmsMenuService menuService;

    @PutMapping
    public DaoCaoResult updateMenu(@Valid @RequestBody UmsMenu umsMenu){
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
    /*
    查询所有菜单
     */
    @GetMapping("/list")
    public DaoCaoResult searchMenuList(UmsMenuParamDto umsMenuParamDto){
        //分页
        Page<UmsMenu> page=new Page<>();
        //分页条件
        if(ObjectUtil.isNotNull(umsMenuParamDto.getPageNum()) && umsMenuParamDto.getPageNum()>1){
            page.setCurrent(umsMenuParamDto.getPageNum());
        }
        if(ObjectUtil.isNotNull(umsMenuParamDto.getPageSize()) && umsMenuParamDto.getPageSize()>0){
            page.setSize(umsMenuParamDto.getPageSize());
        }
        //有搜索功能，用wrapper传
        LambdaQueryWrapper<UmsMenu> wrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotEmpty(umsMenuParamDto.getMenuName())){
            wrapper.eq(UmsMenu::getMenuName,umsMenuParamDto.getMenuName());
        }
        if(StrUtil.isNotEmpty(umsMenuParamDto.getPerms())){
            wrapper.eq(UmsMenu::getPerms,umsMenuParamDto.getPerms());
        }
        //分页查询一定先查一级目录
        wrapper.eq(UmsMenu::getParentId,0);
        menuService.page(page,wrapper);
        //获取一级目录数据
        List<UmsMenu> records=page.getRecords();
        //查询子目录
        List<Long> parentIds = records.stream().map(UmsMenu::getId).toList();
        //根据父Id查询子目录
        List<UmsMenu> umsMenus = menuService.list(new LambdaQueryWrapper<UmsMenu>().in(UmsMenu::getParentId, parentIds).or().in(UmsMenu::getId, parentIds));
        //通过递归描述children
        List<UmsMenu> menuList = buildChildren(umsMenus);
        DaoCaoPageResult<UmsMenu> umsMenuDaoCaoPageResult = new DaoCaoPageResult<>();
        umsMenuDaoCaoPageResult.setTotal(page.getTotal());
        umsMenuDaoCaoPageResult.setList(menuList);
        return DaoCaoResult.success(umsMenuDaoCaoPageResult);
    }
    private List<UmsMenu> buildChildren(List<UmsMenu> menuList){
        //首先获取一级路由
        List<UmsMenu> parentMenu = menuList.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
        //转换对象类型
        for(UmsMenu menu : parentMenu){
            List<UmsMenu> childrenList=buildTree(menuList,menu.getId());
            menu.setChildren(childrenList);
        }
        return parentMenu;
    }
    /*
    获取所有子节点，递归获取
     */
    private List<UmsMenu> buildTree(List<UmsMenu> allMenu,long parentId){
        List<UmsMenu> childrenList = new ArrayList<>();
        //遍历所有的数据
        for(UmsMenu menu: allMenu){
            //判断menu的parentId是否与传进来的相同
            if(menu.getParentId().equals(parentId)){
                childrenList.add(menu);
            }
        }
        //递归childrenList可能还有子节点
        for(UmsMenu childrenItem : childrenList){
            childrenItem.setChildren(buildTree(allMenu,childrenItem.getId()));
        }
        return childrenList;
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
