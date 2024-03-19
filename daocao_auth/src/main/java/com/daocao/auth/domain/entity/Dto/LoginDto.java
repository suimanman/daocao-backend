package com.daocao.auth.domain.entity.Dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginDto implements Serializable {
    public String account;
    public String password;
}
