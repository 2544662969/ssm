package com.zhjg.ssm.service;

import java.util.List;

import com.zhjg.ssm.pojo.SysRole;

public interface SysRoleService {

	List<SysRole> loadSysRoles();

	void addSysRole(SysRole sysRole);

	List<SysRole> loadOwnedSysRoles(String userId);

	List<SysRole> loadUnownedSysRoles(String userId);

	void saveUserRoles(String userId, String[] addRoleIds, String[] removeRoleIds);

}
