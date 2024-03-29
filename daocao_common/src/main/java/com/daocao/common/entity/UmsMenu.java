package com.daocao.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("ums_menu")
public class UmsMenu implements Serializable {
    @TableId
    private  Long id;
    private Long parentId;
    private  String menuName;
    private  Integer menuType;
    private  Integer sort;
    private  String path;
    private  String componentPath;
    private  String perms;
    private  String icon;
    private  String status;
    @TableLogic
    private  Integer deleted;
    private  String remark;
    private  String creator;
    private  String updater;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private  LocalDateTime updateTime;
    @TableField(exist = false)
    private List<UmsMenu> children=new ArrayList<>();
}
