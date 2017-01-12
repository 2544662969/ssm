<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="styles/bootstrap.css">
<script src="scripts/jquery-1.10.1.min.js"></script>
<script src="scripts/bootstrap.js"></script>
<title>用户注册</title>
</head>
<body>

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title" align="center">注册</h3>
		</div>
		<div class="panel-body" align="center">
			<form action="<%=basePath%>user/regist" method="post" id="registForm"
				name="registForm">
				用户名：<input type="text" name="username"><br>
				帐号：<input type="text" name="loginname"><br>
				密码：<input type="password" name="password"><br>
				确认密码：<input type="password" name="confirm"><br>
				邮箱：<input type="text" name="email"><br>
				<button type="submit" class="btn btn-default">注册</button>
			</form>
		</div>
	</div>
</body>
</html>