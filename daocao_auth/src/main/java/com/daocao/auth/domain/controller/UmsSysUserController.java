package com.daocao.auth.domain.controller;

import com.daocao.auth.domain.entity.UmsSysUser;
import com.daocao.auth.domain.service.IUmsSysUserService;
import com.daocao.common.response.DaoCaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ums/sysuser")
public class UmsSysUserController {

    @Autowired
    IUmsSysUserService sysUserService;

    /*
    新增用户接口
     */
    @PostMapping
    public DaoCaoResult addSysUser(@RequestBody UmsSysUser user){
        boolean flag= sysUserService.save(user);
        if(flag){
            return DaoCaoResult.success();
        }else{
            return DaoCaoResult.error();
        }
    }
    /*
    查询用户接口
     */
    @GetMapping
    public DaoCaoResult searchList(){
        List<UmsSysUser> list = sysUserService.list();
        list.forEach(System.out::println);
        return DaoCaoResult.success(list);
    }
}
