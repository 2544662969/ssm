package com.zhjg.ssm.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhjg.ssm.pojo.SysUser;
import com.zhjg.ssm.service.UserLoginService;

@Controller
@RequestMapping("/user")
public class UserLoginController {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserLoginService userLoginService;

	/**
	 * 用户登录
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(SysUser user, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginname(), user.getPassword());
		try{
			token.setRememberMe(true);
			subject.login(token);
			logger.info(subject == SecurityUtils.getSubject());
		}catch(AuthenticationException e){
			logger.info("用户名密码不正确");
			model.addAttribute("error", "用户名密码不正确");
			return "loginerror";
		}
		model.addAttribute("sysUser", user);
		return "main";
	}
	
	/**
	 * 用户注册
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(SysUser user, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String salt = UUID.randomUUID().toString();
		System.out.println("mima:"+user.getPassword());
		System.out.println("salt:"+salt);
		user.setPassword(new Md5Hash(user.getPassword(), salt).toString());
		System.out.println("password:"+user.getPassword());
		user.setSalt(salt);
		userLoginService.regist(user);
		return "main";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().invalidate();
		return "login";
	}

	@RequestMapping("/jsontest")
	public String jsontest() {
		return "jsontest";
	}

	@RequestMapping("/json")
	@ResponseBody
	public SysUser json(@RequestBody SysUser user) {
		System.out.println(user.getUsername() + ":" + user.getPassword());
		return user;
	}

}
