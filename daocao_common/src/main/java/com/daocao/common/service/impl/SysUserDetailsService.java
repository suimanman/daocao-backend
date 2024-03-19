package com.daocao.common.service.impl;

import com.daocao.common.entity.UmsSysUser;
import com.daocao.common.entity.vo.LoginUserVo;
import com.daocao.common.mapper.UmsSysUserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysUserDetailsService implements UserDetailsService {
    @Resource
    UmsSysUserMapper umsSysUserMapper;
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
        //根据角色查询权限 menu
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setSysUser(sysUser);
        loginUserVo.setId(sysUser.getId());
        return loginUserVo;
    }
}
