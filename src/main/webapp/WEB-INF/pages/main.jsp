<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>xxx管理系统</title>
<style>
.main {
	position: absolute;
	left: 1000px right:0;
	top: 50px;
	width: 1050px;
	height: 849px;
	margin-left: 230px;
	border: 1px solid #666666;
};
.header{
	position: absolute;
	top:10px;
	margin-left: 230px;
}
</style>
<link rel="stylesheet" href="<%=basePath%>styles/bootstrap.css">
<link rel="stylesheet" href="<%=basePath%>styles/sidebar-menu.css">
<link rel="stylesheet" href="<%=basePath%>styles/main.css">
<script src="<%=basePath%>scripts/jquery-1.10.1.min.js"></script>
<script src="<%=basePath%>scripts/bootstrap.js"></script>
<script src="<%=basePath%>scripts/angular.js"></script>
<script src="<%=basePath%>scripts/sidebar-menu.js"></script>
</head>
<body ng-controller="mainCtrl">

	<div class="header"><div align="right">当前登录用户：<a style="margin-right: 20px;"><strong><%=SecurityUtils.getSubject().getPrincipal() %></strong></a></div></div>
	<!-- 侧边栏-->
	<aside class="main-sidebar">
	<section class="sidebar">
	<ul class="sidebar-menu" id="menuTree" >
		
	</ul>
	</section></aside>
	<div class="main"><img src="<%=basePath%>images/hrwelcome.jpg"></div>
	
</body>
<script>
	//菜单显示与隐藏
	function hideOrShow(id){
		if($("#"+id).attr("style")==''){
			$("#"+id).attr("style",'display:none');
		}else{
			$("#"+id).attr("style",'');
		}
	}

   //加载菜单
  $.get("<%=basePath%>menu/loadSysMenu").
   success(function (result) {
	   console.log(result);
	   $("#menuTree").html(result);
   }).error(function () {
    	console.log('获取失败');
   });
   
   
   //指定在#main中显示新页面内容
   function openUrl(url){
	   $('.main').load('<%=basePath%>'+url);
   }
</script>