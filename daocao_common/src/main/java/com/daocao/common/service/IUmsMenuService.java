package com.daocao.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daocao.common.entity.UmsMenu;
import com.daocao.common.entity.vo.RouterVo;

import java.util.List;

public interface IUmsMenuService extends IService<UmsMenu> {
    List<RouterVo> searchSelfMenu();

    List<RouterVo> searchMenuList();
}
