package com.zhjg.ssm.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhjg.ssm.mapper.SysUserMapper;
import com.zhjg.ssm.pojo.SysUser;
import com.zhjg.ssm.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SysUserMapper mapper;
	
	@Override
	public List<SysUser> loadSysUsers() {
		return mapper.getAll();
	}

	@Override
	public void deleteSysUser(String id) throws Exception{
		logger.info("id>>>>>>>>>>>>>>"+id);
		mapper.deleteSysUser(id);
	}

}
