package com.zhjg.ssm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhjg.ssm.pojo.SysRole;

public interface SysRoleMapper {

	List<SysRole> getAll();

	List<String> getRoleNamesByLoginname(String loginname);

	void addSysRole(SysRole role);

	List<SysRole> getSysRolesByUserId(String userId);

	List<SysRole> getOtherSysRolesByUserId(String userId);

	void addUserRoles(@Param("userId") String userId, @Param("addRoleIds") String[] addRoleIds);

	void deleteUserRoles(@Param("userId") String userId, @Param("removeRoleIds") String[] removeRoleIds);

}