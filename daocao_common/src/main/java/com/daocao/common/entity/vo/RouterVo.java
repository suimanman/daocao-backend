package com.daocao.common.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.daocao.common.entity.UmsMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RouterVo implements Serializable {

    private Long id;
    private Long parentId;
    private  String menuName;
    private  Integer menuType;
    private  String path;
    private  String componentPath;
    private  String icon;
    private List<RouterVo> children=new ArrayList<>();
}
