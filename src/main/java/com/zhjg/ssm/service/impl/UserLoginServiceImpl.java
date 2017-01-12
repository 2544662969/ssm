package com.zhjg.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhjg.ssm.mapper.SysUserMapper;
import com.zhjg.ssm.pojo.SysUser;
import com.zhjg.ssm.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private SysUserMapper mapper;

	@Override
	public SysUser login(SysUser user) {
		return mapper.selectByLoginname(user.getLoginname()); 
	}

	@Override
	public void regist(SysUser user) {
		mapper.createUser(user);
	}
}
