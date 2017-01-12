package com.zhjg.ssm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhjg.ssm.pojo.SysUser;
import com.zhjg.ssm.service.SysUserService;

@Controller
@RequestMapping("/sysUser")
public class UserController {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping(value="/loadSysUsers", method=RequestMethod.GET)
	@ResponseBody
	public List<SysUser> loadSysUsers(){
		return sysUserService.loadSysUsers();
	}
	
	/**
	 * 用户删除
	 * @return
	 */
	@RequiresPermissions("user:del")
	@RequestMapping(value="/deleteSysUser", method=RequestMethod.POST)
	@ResponseBody
	public String deleteSysUser(HttpServletRequest request){
		try{
			logger.info("id>>>>>>>>>>>>>>"+request.getParameter("id"));
			sysUserService.deleteSysUser(request.getParameter("id"));
		}catch(Exception e){
			return e.getMessage();
		}
		return "success";
	}
	
}
