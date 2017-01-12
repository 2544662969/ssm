package com.zhjg.ssm.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhjg.ssm.mapper.SysMenuMapper;
import com.zhjg.ssm.pojo.SysMenu;
import com.zhjg.ssm.service.SysMenuService;
import com.zhjg.ssm.util.MenuTreeBuilderUtil;
import com.zhjg.ssm.util.RedisUtil;

@Service
public class SysMenuServiceImpl implements SysMenuService {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private RedisUtil redis;
	
	@Autowired
	private SysMenuMapper mapper;
	
	@Override
	public String loadSysMenu() {
		String menuTree = redis.get("menuTree");
		if(menuTree == null || menuTree.trim().equals("")){
			List<SysMenu> sysMenus = mapper.getAll();
			menuTree = MenuTreeBuilderUtil.buildMenuTree(sysMenus);
			redis.put("menuTree", menuTree);
			logger.info("data from db");
		}
		return menuTree;
	}

}
