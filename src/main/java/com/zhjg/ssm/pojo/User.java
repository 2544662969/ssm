package com.zhjg.ssm.pojo;

import java.io.Serializable;

/**
 * ½ö×÷Jedis²âÊÔÓÃ
 * @author 327084
 *
 */
public class User implements Serializable{

	private static final long serialVersionUID = -786139355369407696L;
	private String id;
	private String name;
	private String password;
	
	public User() {}
	public User(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
