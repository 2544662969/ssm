package com.zhjg.ssm.service;

import java.util.List;

import com.zhjg.ssm.pojo.SysUser;

public interface SysUserService {

	List<SysUser> loadSysUsers();

	void deleteSysUser(String id) throws Exception;



}
