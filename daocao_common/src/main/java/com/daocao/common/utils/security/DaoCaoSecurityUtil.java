package com.daocao.common.utils.security;


import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.constants.HttpStatus;
import com.daocao.common.entity.vo.LoginUserVo;
import com.daocao.common.exception.ServiceException;
import org.springframework.security.core.Authentication;
/*
获取登录的用户信息
 */
import org.springframework.security.core.context.SecurityContextHolder;

public class DaoCaoSecurityUtil {
    //获取Authentication
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    //获取用户
    public static LoginUserVo getLoginUser(){
        return (LoginUserVo) getAuthentication().getPrincipal();
    }

    //获取用户id
    public static Long getUserId(){
        Long userId = getLoginUser().getId();
        if(ObjectUtil.isNull(userId)){
            throw new ServiceException(HttpStatus.FORBIDDEN,"");
        }
        return userId;
    }

    /*
    获取用户名
     */
    public static String getUsername(){
        String username = getLoginUser().getUsername();
        if(ObjectUtil.isNull(username)){
            throw new ServiceException(HttpStatus.FORBIDDEN,"");
        }
        return username;
    }

}
