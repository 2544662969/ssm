package com.zhjg.ssm.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	/**
	 * 项目首页
	 * @return
	 */
	@RequestMapping(value={"/","/index","/home"})
	public String index(){
		return "login";
	}
	
	@RequestMapping("/regist")
	public String redirectToRegist(){
		return "regist";
	}

	/**
	 * 用户管理
	 * @return
	 */
	@RequiresPermissions("sys:usermgp")
	@RequestMapping("/sys/user")
	public String userList(){
		return "userlist";
	}
	
	/**
	 * 角色管理
	 * @return
	 */
	@RequestMapping("/sys/role")
	public String roleList(){
		return "rolelist";
	}
	
	/**
	 * 权限管理
	 * @return
	 */
	@RequestMapping("/sys/permission")
	public String permissionList(){
		return "permissionlist";
	}
	
	/**
	 * 菜单管理
	 * @return
	 */
	@RequestMapping("/sys/menu")
	public String menuList(){
		return "menulist";
	}
	
	@RequestMapping("/denied")
	public String denied(){
		return "denied";
	}
}
