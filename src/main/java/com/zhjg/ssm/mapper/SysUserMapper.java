package com.zhjg.ssm.mapper;

import java.util.List;

import com.zhjg.ssm.pojo.SysUser;


public interface SysUserMapper {

	List<SysUser> selectByUser(SysUser user);

	List<SysUser> getAll();

	SysUser selectByLoginname(String loginname);

	void createUser(SysUser user);

	void deleteSysUser(String id);
}