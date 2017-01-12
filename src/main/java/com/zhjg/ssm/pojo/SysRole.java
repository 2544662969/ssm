package com.zhjg.ssm.pojo;

import java.io.Serializable;

public class SysRole implements Serializable{

	private static final long serialVersionUID = 2999974583131832201L;
	
	private String id;
	private String roleName;
	private String description;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
