package com.daocao.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.constants.HttpStatus;
import com.daocao.common.entity.Dto.LoginDto;
import com.daocao.common.entity.vo.LoginUserVo;
import com.daocao.common.exception.ServiceException;
import com.daocao.common.service.IAuthService;
import com.daocao.common.utils.JwtUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    JwtUtils jwtUtils;


    @Override
    public String login(LoginDto loginDto){
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loginDto.getAccount(), loginDto.getPassword());
        //调用loadUserByUserName
        Authentication authenticate = authenticationManager.authenticate(authentication);
        //获取用户信息,返回的就是UserDetails
        LoginUserVo loginUser=(LoginUserVo) authenticate.getPrincipal();
        //根据loginUser创建token
        if(ObjectUtil.isNull(loginUser)){
            throw new ServiceException(HttpStatus.UNAUTHORIZED,"认证失败！");
        }
        //创建token,此处的Token由UUID编码而成JWT字符串
        String token = jwtUtils.createToken(loginUser);
        log.info("token------------>{}",token);
        return token;
    }
}
