package com.daocao.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("ums_sys_user")
public class UmsSysUser implements Serializable {
    @TableId
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String mobile;
    private Integer sex;
    private String avatar;
    private String password;
    private Integer status;
    private String creator;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private  LocalDateTime updateTime;
    private String updater;
    private String remark;
    //逻辑删除，mybatis默认0是未删除，1是已删除
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    //角色信息
    private List<UmsRole> roleList=new ArrayList<>();
}
