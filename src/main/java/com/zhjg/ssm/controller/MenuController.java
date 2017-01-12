package com.zhjg.ssm.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhjg.ssm.service.SysMenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 加载系统菜单
	 * @return
	 */
	@RequestMapping(value="/loadSysMenu", method=RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String loadSysMenu(){
		logger.info("...............loadSysMenu of class MenuController is running..............");
		return sysMenuService.loadSysMenu();
	}
	
}
