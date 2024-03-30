package com.daocao.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daocao.common.entity.UmsMenu;
import com.daocao.common.entity.vo.RouterVo;

import java.util.List;

public interface IUmsMenuService extends IService<UmsMenu> {
    List<RouterVo> searchSelfMenu();

    List<RouterVo> searchMenuList();

    int saveMenu(UmsMenu umsMenu);

    UmsMenu searchInfo(Long id);

    int updateMenu(UmsMenu umsMenu);

    int removeMenu(Long[] id);
}
