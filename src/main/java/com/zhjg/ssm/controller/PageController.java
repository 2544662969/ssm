package com.zhjg.ssm.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	/**
	 * ��Ŀ��ҳ
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
	 * �û�����
	 * @return
	 */
	@RequiresPermissions("sys:usermgp")
	@RequestMapping("/sys/user")
	public String userList(){
		return "userlist";
	}
	
	/**
	 * ��ɫ����
	 * @return
	 */
	@RequestMapping("/sys/role")
	public String roleList(){
		return "rolelist";
	}
	
	/**
	 * Ȩ�޹���
	 * @return
	 */
	@RequestMapping("/sys/permission")
	public String permissionList(){
		return "permissionlist";
	}
	
	/**
	 * �˵�����
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
