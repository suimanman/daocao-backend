package com.daocao.sysuser.controller;

import com.daocao.common.response.DaoCaoResult;
import com.daocao.sysuser.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/user")
public class UserController {

    @Resource
    SysUserService sysUserService;
    @GetMapping("/info")
    public DaoCaoResult searchUserInfo(){
        return DaoCaoResult.success(sysUserService.searchUserInfo());
    }
}
