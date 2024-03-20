package com.daocao.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.entity.UmsMenu;
import com.daocao.common.entity.UmsRole;
import com.daocao.common.entity.UmsSysUser;
import com.daocao.common.entity.vo.LoginUserVo;
import com.daocao.common.mapper.UmsMenuMapper;
import com.daocao.common.mapper.UmsSysUserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysUserDetailsService implements UserDetailsService {
    @Resource
    UmsSysUserMapper umsSysUserMapper;
    @Resource
    UmsMenuMapper umsMenuMapper;
    /*
    实现方法,在此方法中根据用户名查询用户
    账号：用户名/手机号/邮箱，通过正则表达式判断账号类型
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        //TODO 验证账号类型
        int accountType=0;
        //根据账号查询用户，同时将角色查询出来
        UmsSysUser sysUser= umsSysUserMapper.selectUserByUserName(account,accountType);
        log.info("sysUser   -------------{}",sysUser);
        //权限查询是根据角色查询的，首先获取所有角色id
        if(ObjectUtil.isNotNull(sysUser)){
            List<UmsRole> roleList=sysUser.getRoleList();
            //取出id
            List<Long> roleIds=roleList.stream().map(UmsRole::getRoleId).toList();
            log.info("========{}",roleIds);
            //查询所有菜单
            List<UmsMenu> menuList=umsMenuMapper.selectByRoleIds(roleIds);
            //获取List中的权限字段
            List<String> perms = menuList.stream().map(UmsMenu::getPerms).collect(Collectors.toList());
            log.info("权限=======》{}",perms);
            sysUser.setPerms(perms);
        }
        //根据角色查询权限 menu
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setSysUser(sysUser);
        loginUserVo.setId(sysUser.getId());
        return loginUserVo;
    }
}
