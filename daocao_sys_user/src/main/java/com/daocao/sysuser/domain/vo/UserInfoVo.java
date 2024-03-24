package com.daocao.sysuser.domain.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserInfoVo implements Serializable {
    private Long id;
    private String nickname;

    private String avatar;
}
