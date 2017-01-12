<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

	
					<shiro:hasPermission name='sys:pmp'>
						<li ondblclick='hideOrShow(ccdb2a78-d2b0-4f2d-9a5a-3255c5fb0c7f);'
							class='level-2'><a
							href="javascript:openUrl('sys/permission')">权限管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name='sysmgp:role'>
						<li ondblclick='hideOrShow(3957d919-4c36-443a-865f-a7c96da1ebea);'
							class='level-2'><a href="javascript:openUrl('sys/role')">角色管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name='sys:usermgp'>
						<li ondblclick='hideOrShow(66ccf32c-0619-4cbb-b7aa-2e22e5726e5b);'
							class='level-2'><a href="javascript:openUrl('sys/user')">用户管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name='sys:menu'>
						<li ondblclick='hideOrShow(60b2a3c7-d33c-4c52-b8d6-46676d7fcfd9);'
							class='level-2'><a href="javascript:openUrl('sys/menu')">菜单管理</a></li>
					</shiro:hasPermission>
</body>
</html>