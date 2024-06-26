package com.daocao.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daocao.common.constants.HttpStatus;
import com.daocao.common.entity.UmsMenu;
import com.daocao.common.entity.UmsRole;
import com.daocao.common.entity.vo.RouterVo;
import com.daocao.common.exception.ServiceException;
import com.daocao.common.mapper.UmsMenuMapper;
import com.daocao.common.mapper.UmsRoleMapper;
import com.daocao.common.service.IUmsMenuService;
import com.daocao.common.utils.security.DaoCaoSecurityUtil;
import jakarta.annotation.Resource;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements IUmsMenuService {

    @Resource
    UmsRoleMapper roleMapper;
    /*
    获取个人菜单列表
     */
    @Override
    public List<RouterVo> searchSelfMenu() {
        Long userId = DaoCaoSecurityUtil.getUserId();
        List<Long> roleList=roleMapper.selectUserById(userId);
        List<UmsMenu> menuList = baseMapper.selectByRoleIds(roleList);
        //通过递归设置菜单的树性结构
        //1.获取所有1级菜单【parentId = 0 】
        //遍历1级菜单，获取子元素
        List<RouterVo> router = getRouter(menuList);
        router.forEach(System.out::println);
        return router;
    }

    @Override
    public List<UmsMenu> searchMenuList() {
        return baseMapper.selectList(null);
    }

    @Override
    public int saveMenu(UmsMenu umsMenu) {
        String creator=DaoCaoSecurityUtil.getUsername();
        umsMenu.setCreator(creator);
        umsMenu.setUpdater(creator);
        //判断是否已经存在
        Long parentId=umsMenu.getParentId();
        String menuName=umsMenu.getMenuName();
        String path=umsMenu.getPath();
        LambdaQueryWrapper<UmsMenu> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(UmsMenu::getParentId,parentId).eq(UmsMenu::getMenuName,menuName).or().eq(UmsMenu::getPath,path);
        Long count=baseMapper.selectCount(wrapper);
        if(count>0) return -1;
//            throw new ServiceException(HttpStatus.ERROR,"该菜单或路径已存在！");
        return baseMapper.insert(umsMenu);
    }

    @Override
    public UmsMenu searchInfo(Long id) {
        return baseMapper.selectById(id);

    }

    @Override
    public int updateMenu(UmsMenu umsMenu) {
        String updater=DaoCaoSecurityUtil.getUsername();
        umsMenu.setUpdater(updater);
        //判断是否已经存在
        Long parentId=umsMenu.getParentId();
        String menuName=umsMenu.getMenuName();
        String path=umsMenu.getPath();
        LambdaQueryWrapper<UmsMenu> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(UmsMenu::getParentId,parentId).eq(UmsMenu::getMenuName,menuName).or().eq(UmsMenu::getPath,path);
        Long count=baseMapper.selectCount(wrapper);
        if(count>0)
            return -1;
        return baseMapper.updateById(umsMenu);
    }

    @Override
    public int removeMenu(Long[] id) {
        return baseMapper.deleteBatchIds(Arrays.asList(id));
    }

    /*
    获取路由
     */
    private List<RouterVo> getRouter(List<UmsMenu> menuList){
        List<RouterVo> routerVos=new ArrayList<>();
        //首先获取一级路由
        List<UmsMenu> parentMenu = menuList.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
        //转换对象类型
        for(UmsMenu menu:parentMenu){
            RouterVo routerVo=new RouterVo();
            BeanUtil.copyProperties(menu,routerVo);
            routerVos.add(routerVo);
        }
        //循环一级路由，获取所有子菜单
        for (RouterVo routerVo : routerVos) {
            //获取所有子节点
            List<RouterVo> childrenList=buildTree(menuList,routerVo.getId());
            routerVo.setChildren(childrenList);

        }
        return routerVos;
    }
    /*
    获取所有子节点，递归获取
     */
    private List<RouterVo> buildTree(List<UmsMenu> allMenu,long parentId){
        List<RouterVo> childrenList = new ArrayList<>();
        //遍历所有的数据
        for(UmsMenu menu: allMenu){
            //判断menu的parentId是否与传进来的相同
            if(menu.getParentId().equals(parentId)){
                RouterVo routerVo=new RouterVo();
                BeanUtil.copyProperties(menu,routerVo);
                childrenList.add(routerVo);
            }
        }
        //递归childrenList可能还有子节点
        for(RouterVo childrenItem : childrenList){
            childrenItem.setChildren(buildTree(allMenu,childrenItem.getId()));
        }
        return childrenList;
    }
}
