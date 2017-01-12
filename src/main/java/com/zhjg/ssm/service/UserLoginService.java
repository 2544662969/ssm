package com.zhjg.ssm.service;

import com.zhjg.ssm.pojo.SysUser;

public interface UserLoginService {

	public SysUser login(SysUser user);

	public void regist(SysUser user);
}
