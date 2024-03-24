package com.daocao.support.config.filter;

import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.entity.vo.LoginUserVo;
import com.daocao.common.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*
该接口在请求前会执行一次，获取request中的数据，其中token就在请求头中
获取token，根据token从redis中获取用户信息
 */
@Component
@Slf4j
public class JwtAuthticationFilter extends OncePerRequestFilter {

    @Resource
    JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取登录用户
        LoginUserVo loginUserVo=(LoginUserVo) jwtUtils.getLoginUser(request);
        //判断是否为null
        if(ObjectUtil.isNotNull(loginUserVo)){
            //鉴权，跳转的时候需要访问 /index页面
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginUserVo, null, loginUserVo.getAuthorities());
            //将用户信息存储到SecurityContext中，SecurityContext存储到SecurityContextHolder中
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        //放行,交由后边的过滤器执行，如果没有登录，就会呗登录拦截器UsernamePasswordAuthenticationFilter拦截
        filterChain.doFilter(request,response);
    }
}
