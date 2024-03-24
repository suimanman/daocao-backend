package com.daocao.sysuser.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.constants.HttpStatus;
import com.daocao.common.entity.UmsSysUser;
import com.daocao.common.exception.ServiceException;
import com.daocao.common.mapper.UmsSysUserMapper;
import com.daocao.common.utils.security.DaoCaoSecurityUtil;
import com.daocao.sysuser.domain.vo.UserInfoVo;
import com.daocao.sysuser.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Resource
    UmsSysUserMapper sysUserMapper;
    @Override
    public UserInfoVo searchUserInfo() {
        Long userId = DaoCaoSecurityUtil.getUserId();
        UmsSysUser sysUser = sysUserMapper.selectById(userId);
        if(ObjectUtil.isNull(sysUser)){
            throw new ServiceException(HttpStatus.FORBIDDEN,"");
        }
        UserInfoVo userInfoVo=new UserInfoVo();
        BeanUtil.copyProperties(sysUser,userInfoVo);
        return userInfoVo;
    }
}
