package com.zhjg.ssm.pojo;

import java.io.Serializable;

public class SysMenu implements Serializable{

	private static final long serialVersionUID = 8593577386250918202L;
	
	private String id;
	private String name;
	private int type;
	private SysPermission permission;
	private String url;
	private String parentId;
	private int menuOrder;
	private String description;
	private String isLeaf;//Y;N
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public SysPermission getPermission() {
		return permission;
	}
	public void setPermission(SysPermission permission) {
		this.permission = permission;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public int getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	
}
