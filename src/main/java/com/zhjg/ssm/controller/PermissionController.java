package com.zhjg.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhjg.ssm.pojo.SysPermission;
import com.zhjg.ssm.service.SysPermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private SysPermissionService sysPermissionService;
	
	@RequestMapping(value="/loadSysPermission", method=RequestMethod.GET)
	@ResponseBody
	public List<SysPermission> loadSysPermission(){
		return sysPermissionService.loadSysPermission();
	}
}
