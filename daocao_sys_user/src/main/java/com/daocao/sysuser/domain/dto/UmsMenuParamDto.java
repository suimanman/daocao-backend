package com.daocao.sysuser.domain.dto;

import lombok.Data;

@Data
public class UmsMenuParamDto {

    private String menuName;
    private String perms;
    //当前页
    private Long pageNum;
    //每页数据量
    private Long pageSize;
}
