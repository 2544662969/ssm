package com.zhjg.ssm.pojo;

import java.io.Serializable;

public class SysUser implements Serializable{

	private static final long serialVersionUID = 2907070097730275623L;

	private String id;

    private String username;

    private String password;
    
    //√‹‘ø
    private String salt;
    //≤ª÷ÿ∏¥◊¢≤·
    private String loginname;
    
    private String dept;
    
    private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
   
}