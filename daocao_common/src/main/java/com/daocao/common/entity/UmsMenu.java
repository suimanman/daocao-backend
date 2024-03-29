package com.daocao.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("ums_menu")
public class UmsMenu implements Serializable {
    @TableId(type = IdType.AUTO)
    private  Long id;
    private Long parentId;
    @NotNull(message = "请填写菜单名")
    private  String menuName;
    @NotNull(message = "请填写菜单类型")
    private  Integer menuType;
    private  Integer sort;
    @NotNull(message = "请填写路径")
    private  String path;
    private  String componentPath;
    @NotNull(message = "权限不能为空")
    private  String perms;
    @NotNull(message = "icon不能为空")
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
