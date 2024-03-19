package com.daocao.common.entity.Dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginDto implements Serializable {
    public String account;
    public String password;
    private Integer rememberMe;
}
