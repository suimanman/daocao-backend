package com.daocao.common.utils;

import com.daocao.common.constants.CacheConstants;
import com.daocao.common.entity.UmsSysUser;
import com.daocao.common.entity.vo.LoginUserVo;
import com.daocao.common.utils.redis.RedisCacheUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;
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
        Map<String, Object> claims=new HashMap<>();
        claims.put("token",token);
        //将用户数据缓存到redis中
        redisCacheUtil.setCacheObject(CacheConstants.LOGIN_USER_KEY+token,loginUserVo,30, TimeUnit.MINUTES);
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

}
