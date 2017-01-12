package com.zhjg.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhjg.ssm.mapper.SysPermissionMapper;
import com.zhjg.ssm.pojo.SysPermission;
import com.zhjg.ssm.service.SysPermissionService;
import com.zhjg.ssm.util.RedisUtil;

@Service
public class SysPermissionServiceImpl implements SysPermissionService{

	@Autowired
	private RedisUtil redis;
	
	@Autowired
	private SysPermissionMapper mapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysPermission> loadSysPermission() {
		List<SysPermission> reval = null;
		List<SysPermission> redisPermissions = redis.get("sysPermissions", List.class);
		if(redisPermissions == null || redisPermissions.size() == 0){
			reval = mapper.getAll();
			redis.put("sysPermissions", reval);
			System.out.println("data from db");
		}else{
			reval = redisPermissions;
			System.out.println("data from redis");
		}
		return reval;
	}

}
