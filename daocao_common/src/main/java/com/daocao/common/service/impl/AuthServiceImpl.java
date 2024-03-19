package com.daocao.common.service.impl;

import com.daocao.common.entity.Dto.LoginDto;
import com.daocao.common.service.IAuthService;
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



    @Override
    public String login(LoginDto loginDto){
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loginDto.getAccount(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        //获取用户信息
        Object loginUser=authenticate.getPrincipal();
        //TODO 根据loginUser创建token
        return "token";
    }
}
