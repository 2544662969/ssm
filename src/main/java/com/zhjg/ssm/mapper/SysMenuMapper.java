package com.zhjg.ssm.mapper;

import java.util.List;

import com.zhjg.ssm.pojo.SysMenu;

public interface SysMenuMapper {

	List<SysMenu> getAll();

	List<SysMenu> getMenuByType(int type);

}