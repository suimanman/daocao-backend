package com.daocao.common.entity.vo;

import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.entity.UmsSysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LoginUserVo implements UserDetails {
    private Long id;
    private String token;
    //用户信息
    private UmsSysUser sysUser;
    //用户权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> perms=sysUser.getPerms();
        //判空，返回数据
        if(ObjectUtil.isNotEmpty(perms)){
            return perms.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return sysUser.getStatus() == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return sysUser.getStatus() == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return sysUser.getStatus() == 0;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getStatus() == 0;
    }
}
