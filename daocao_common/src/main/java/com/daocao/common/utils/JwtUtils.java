package com.daocao.common.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.daocao.common.constants.CacheConstants;
import com.daocao.common.entity.UmsSysUser;
import com.daocao.common.entity.vo.LoginUserVo;
import com.daocao.common.utils.redis.RedisCacheUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*
通过JWT生成token和解析token，刷新token
 */
@Component
public class JwtUtils {

    @Resource
    RedisCacheUtil redisCacheUtil;
    private String secret="wmc123456";
    /*
    创建token,将用户数据存放在redis中
    可以将UUID当作redis的key
     */
    public String createToken(LoginUserVo loginUserVo){
        UmsSysUser sysUser=loginUserVo.getSysUser();
        //创建map
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //将UUID存储到登录用户中，可以在后台系统中根据token值获取redis数据
        loginUserVo.setToken(token);
        //设置登录时间
        loginUserVo.setLoginTime(System.currentTimeMillis());
        Map<String, Object> claims=new HashMap<>();
        claims.put("token",token);
        //配置到redis
        refreshToken(loginUserVo);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    /*
    解析token
     */
    public Claims parseToken(String token){
        //解析token
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
    /*
    获取登录用户
    根据token解析，从redis中获取
    刷新token
     */
    public Object getLoginUser(HttpServletRequest request) {
        //通过jwt加密过的
        String token = request.getHeader("Daocao-Authorization");
        if(StrUtil.isNotEmpty(token)){
            //解析token
            Claims claims = parseToken(token);
            System.out.println("claims============>"+claims);
            String parseToken = (String) claims.get("token");
            System.out.println("parseToken==========>"+parseToken);
            //从redis中获取数据
            LoginUserVo loginUserVo=redisCacheUtil.getCacheObject(CacheConstants.LOGIN_USER_KEY+parseToken);
            //TODO 刷新token
            long loginTime=loginUserVo.getLoginTime();
            long currentTime=System.currentTimeMillis();
            long millis=currentTime/1000/60-loginTime/1000/60;
            if(millis>=20)
                refreshToken(loginUserVo);
            System.out.println("loginUserVo==========>"+loginUserVo);
            return loginUserVo;
        }
        return null;
    }
    //刷新token
    private void refreshToken(LoginUserVo loginUserVo){
        //将用户数据缓存到redis中
        redisCacheUtil.setCacheObject(CacheConstants.LOGIN_USER_KEY+loginUserVo.getToken(),loginUserVo,30, TimeUnit.MINUTES);
    }
}
