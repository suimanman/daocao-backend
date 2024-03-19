package com.daocao.auth.domain.controller;

import com.daocao.auth.domain.entity.Dto.LoginDto;
import com.daocao.common.response.DaoCaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
认证接口
 */
@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {
    /*
    系统用户登录
     */

    @PostMapping("sys")
    public DaoCaoResult sysLogin(@RequestBody LoginDto loginDto){
        log.info("--------{}",loginDto);
        return DaoCaoResult.success();
    }
}
