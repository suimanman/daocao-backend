package com.daocao.common.service;

import com.daocao.common.entity.Dto.LoginDto;

public interface IAuthService {

    String login(LoginDto loginDto);
}
