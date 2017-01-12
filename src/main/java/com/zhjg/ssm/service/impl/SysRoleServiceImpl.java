package com.zhjg.ssm.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhjg.ssm.mapper.SysRoleMapper;
import com.zhjg.ssm.pojo.SysRole;
import com.zhjg.ssm.service.SysRoleService;
import com.zhjg.ssm.util.RedisUtil;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private RedisUtil redis;
	
	@Autowired
	private SysRoleMapper mapper;
	
	/**
	 * 加载所有角色
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> loadSysRoles() {
		List<SysRole> reval = null;
		List<SysRole> redisRoles = redis.get("sysRoles", List.class);
		if(redisRoles == null || redisRoles.size() == 0){
			reval = mapper.getAll();
			redis.put("sysRoles", reval);
			System.out.println("data from db");
		}else{
			reval = redisRoles;
			System.out.println("data from redis");
		}
		return reval;
	}

	/**
	 * 添加角色
	 */
	@Override
	@RequiresRoles("sys")
	public void addSysRole(SysRole role) {
		//mapper.addSysRole(role);
		logger.info("addSysRole of SysRoleServiceImpl is running");
	}

	@Override
	public List<SysRole> loadOwnedSysRoles(String userId) {
		return mapper.getSysRolesByUserId(userId);
	}

	@Override
	public List<SysRole> loadUnownedSysRoles(String userId) {
		return mapper.getOtherSysRolesByUserId(userId);
	}

	@Override
	public void saveUserRoles(String userId, String[] addRoleIds, String[] removeRoleIds) {
		if(addRoleIds != null && addRoleIds.length > 0){
			mapper.addUserRoles(userId, addRoleIds);
		}
		if(removeRoleIds != null && removeRoleIds.length > 0){
			mapper.deleteUserRoles(userId, removeRoleIds);
		}
		
		
	}

}
