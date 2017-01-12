package com.zhjg.ssm.mapper;

import java.util.List;

import com.zhjg.ssm.pojo.SysPermission;

public interface SysPermissionMapper {

	List<SysPermission> getAll();

	List<SysPermission> findPermissionByName(String userId);

	List<String> getPermissionsByRoleNames(List<String> roleNames);

}