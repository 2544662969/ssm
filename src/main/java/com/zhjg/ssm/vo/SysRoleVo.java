package com.zhjg.ssm.vo;

public class SysRoleVo {

	private String id;
	private String name;
	private String description;
	//当前登录用户是否具备这个角色
	private String isCurUserRole;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsCurUserRole() {
		return isCurUserRole;
	}
	public void setIsCurUserRole(String isCurUserRole) {
		this.isCurUserRole = isCurUserRole;
	}
	
}
