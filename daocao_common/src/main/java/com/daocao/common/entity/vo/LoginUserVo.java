package com.daocao.common.entity.vo;

import com.daocao.common.entity.UmsSysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data
public class LoginUserVo implements UserDetails {
    private Long id;
    private String token;
    //用户信息
    private UmsSysUser sysUser;
    //用户权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
