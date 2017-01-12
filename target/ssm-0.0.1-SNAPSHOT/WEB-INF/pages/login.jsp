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
<title>登录页面</title>
<link rel="stylesheet" href="<%=basePath %>styles/bootstrap.css">
<link rel="stylesheet" href="<%=basePath %>styles/login.css">
<script src="<%=basePath %>scripts/jquery-1.10.1.min.js"></script>
<script src="<%=basePath %>scripts/bootstrap.js"></script>
</head>
<body>
	<div class="login">
		<div class="container">
			<form class="form-horizontal" role="form" action="<%=basePath%>user/login" method="post"
				name="loginForm">
				<h2>请登录</h2>
				<input class="form-control" name="loginname" type="text"
					placeholder="请输入帐号"> <input class="form-control"
					name="password" type="password" placeholder="请输入密码">
				<button type="submit" class="btn btn-primary btn-group-lg">登录</button>
				<p><a href="<%=basePath %>/regist">没有帐号，点此注册</a></p>
			</form>
		</div>
	</div>
</body>
</html>