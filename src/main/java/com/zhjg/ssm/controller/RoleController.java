package com.zhjg.ssm.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhjg.ssm.pojo.SysRole;
import com.zhjg.ssm.service.SysRoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping(value="/loadSysRoles", method=RequestMethod.GET)
	@ResponseBody
	public List<SysRole> loadSysRoles(){
		return sysRoleService.loadSysRoles();
	}
	
	@RequestMapping("/addSysRole")
	@RequiresRoles("sys")
	public String addSysRole(){
		sysRoleService.addSysRole(new SysRole());
		return "main";
	}
	
	/**
	 * 查询用户已拥有角色
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadOwnedSysRoles")
	@RequiresRoles("sys")
	@ResponseBody
	public List<SysRole> loadOwnedSysRoles(HttpServletRequest request){
		String userId = request.getParameter("id");
		return sysRoleService.loadOwnedSysRoles(userId);
		
	}
	
	/**
	 * 查询用户尚未拥有角色
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadUnownedSysRoles")
	@RequiresRoles("sys")
	@ResponseBody
	public List<SysRole> loadUnownedSysRoles(HttpServletRequest request){
		String userId = request.getParameter("id");
		logger.info("id<><><><><>"+userId);
		return sysRoleService.loadUnownedSysRoles(userId);
		
	}
	
	/**
	 * 保存用户角色更改
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/saveUserRoles")
	@RequiresRoles("sys")
	@ResponseBody
	public String saveUserRoles(String userId, String addRoleIds, String removeRoleIds) throws UnsupportedEncodingException{
		try{
			sysRoleService.saveUserRoles(userId, addRoleIds.split(","), removeRoleIds.split(","));
		}catch(Exception e){
			logger.info(e.getMessage());
			logger.info(e.getCause());
			return "failed";
		}
		return "success";
		
		
	}
	
}
