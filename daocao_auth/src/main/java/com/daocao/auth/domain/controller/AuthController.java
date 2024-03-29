package com.daocao.auth.domain.controller;

import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.constants.HttpStatus;
import com.daocao.common.entity.Dto.LoginDto;
import com.daocao.common.exception.ServiceException;
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
        if(loginDto.getAccount() != null && loginDto.getPassword()!=null) {
            log.info("--------{}", loginDto);
            String token = authService.login(loginDto);
            return DaoCaoResult.success().put("token", token);
        }else{
            return DaoCaoResult.error(HttpStatus.ERROR,"请输入正确的用户名和密码！");
        }
    }

}
