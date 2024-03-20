package com.daocao.auth.domain.controller;

import com.daocao.common.entity.Dto.LoginDto;
import com.daocao.common.service.IAuthService;
import com.daocao.common.response.DaoCaoResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/*
认证接口
 */
@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {

    @Resource
    IAuthService authService;
    @Autowired
    PasswordEncoder passwordEncoder;
    /*
    系统用户登录
     */
    @PostMapping("sys")
    public DaoCaoResult sysLogin(@RequestBody LoginDto loginDto){
        log.info("--------{}",loginDto);
        String token=authService.login(loginDto);
        return DaoCaoResult.success().put("token",token);
    }

}
