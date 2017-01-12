<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<title>Insert title here</title>
<script src="<%=basePath%>scripts/jquery-1.10.1.min.js"></script>
<script src="<%=basePath%>scripts/bootstrap.js"></script>
</head>
<body>
	<form role="form" style="padding: 5px 100px; margin-left: 1px;">
		<div class="form-group">
			<label for="name">用户名</label>
			<input type="text" class="form-control" id="name" 
				   placeholder="请输入名称">
			<label for="name">登录名</label>
			<input type="text" class="form-control" id="name" 
				   placeholder="请输入名称">
		</div>
		<button type="submit" class="btn btn-default">查询</button>
	</form>
	
	<table class="table" style="padding: 5px 100px; margin-left: 1px;">
	<caption>基本的表格布局</caption>
   <thead>
      <tr>
         <th>用户名</th>
         <th>登录名</th>
         <th>操作</th>
      </tr>
   </thead>
   <tbody id="users">
   	
   </tbody>
</table>
<div id="roleManage" style="margin-left: 280px; background-color:White; z-index:9999; display:none;">
	<input type="hidden" id="userId" value="">
	<table border="1" style="width: 400px">
		<tr><td colspan="2" align="center">角色管理</td><tr>
		<tr><td>已具有</td><td>未具有</td><tr>
		<tr height="200px">
			<td id="owned" align="left" valign="top">
				<!-- <div><input type="checkbox" value="1" />系统管理员</div> -->
			</td>
			<td id="unowned" align="left" valign="top">
				<!-- <div><input type="checkbox" value="2" />菜单维护</div>
				<div><input type="checkbox" value="3" />普通用户</div> -->
			</td>
		<tr>
		<tr><td colspan="2" align="center"><button onclick="cancel();">取消</button><button onclick="addRole();">添加</button><button onclick="removeRole();">移除</button><button onclick="save();">保存</button></td><tr>
	</table>
</div>
</body>
<script>
	//加载所有用户
	$(function() {
		loadSysUsers();
	})


	//角色管理（打开对话框并加载相关角色）
	function modifyUserRoles(userid){
		//选中的用户
		$("#userId").val(userid);
		//打开对话框
		document.getElementById("roleManage").style.display = '';
		//加载该用户已经具有的角色(text)
		$.ajax({
			type : "POST",
			data: {id:userid},
			url : "<%=basePath %>role/loadOwnedSysRoles",
			dataType : "text",
			success : function(data) {
				var html = '';
				var roles = $.parseJSON(data);
				if(roles.length > 0){
					$.each(roles, function(index, item) {
						html += "<div><input type='checkbox' value='"+item.id+"' />";
						html += item.description;
						html += "</div>";
					});
					console.log(html);
					$('#owned').html(html);
				}
			}
		});
		//加载该用户尚未具有的角色(json)
		$.ajax({
			type : "POST",
			data: {id:userid},
			url : "<%=basePath %>role/loadUnownedSysRoles",
			dataType : "json",
			success : function(data) {
				var html = '';
				if(data.length > 0){
					$.each(data, function(index, item) {
						html += "<div><input type='checkbox' value='"+item.id+"' />";
						html += item.description;
						html += "</div>";
					});
					console.log(html);
					$('#unowned').html(html);
				}
			}
		});
	}

	//角色变动保存
	function save(){
		var userId = $("#userId").val();
		//要添加的角色
		var addRoleIds = [];
		$.each($("#owned input"), function(index, item) {
			if(item.checked==true){
				addRoleIds.push(item.value);
			}
		})
		var addRoleIdsTmp = addRoleIds.join(",");
		//要移除的角色
		var removeRoleIds = [];
		$.each($("#unowned input"), function(index, item) {
			if(item.checked==true){
				removeRoleIds.push(item.value);
			}
		})
		var removeRoleIdsTmp = removeRoleIds.join(",");
		var param = [{ name : 'userId', value : userId},{ name : 'addRoleIds', value : addRoleIdsTmp},{ name : 'removeRoleIds', value : removeRoleIdsTmp}];
		$.ajax({
			type : "POST",
			data: param,
			url : "<%=basePath %>role/saveUserRoles",
			dataType : "text",
			success : function(data) {
				if(data=="success"){
					alert("保存成功!");
					$("#userId").val("");
					$("#owned").html("");
					$("#unowned").html("");
					document.getElementById("roleManage").style.display = 'none';
				}
			}
		});
	}
	
	function cancel(){
		//关闭对话框不做操作
		$("#userId").val("");
		$("#owned").html("");
		$("#unowned").html("");
		document.getElementById("roleManage").style.display = 'none';
	}

	

	
	//加载用户列表
	function loadSysUsers(){
		$.ajax({
			type : "GET",
			url : "<%=basePath %>sysUser/loadSysUsers",
			dataType : "json",
			success : function(data) {
				console.log(data);
				var html = '';
				$.each(data, function(index, item) {
					html += "<tr><td>";
					html += item.username;
					html += "</td><td>";
					html += item.loginname;
					html += "</td><td>";
					html += "<button onclick=\"modifyUserRoles('"+item.id+"')\">角色管理</button>";
					html += "<button onclick=\"modifySysUser('"+item.id+"')\">修改</button>";
					html += "<button onclick=\"deleteSysUser('"+item.id+"');\">删除</button>";
					html += "</td></tr>";
				});
				$('#users').html(html);
			}
		});
	}
	
	function addRole(){
		var unowned = $("#unowned input");
		for(var i=0;i<unowned.length;i++){
			if(unowned[i].checked==true){
				$("#owned").append($(unowned[i]).parent());
			}
		}
		return;
	}
	
	function removeRole(){
		var owned = $("#owned input");
		for(var i=0;i<owned.length;i++){
			if(owned[i].checked==true){
				$("#unowned").append($(owned[i]).parent());
			}
		}
		return;
	}
	
	//删除用户
	function deleteSysUser(userid){
		$.ajax({
			type : "POST",
			data: {id:userid},
			url : "<%=basePath %>sysUser/deleteSysUser",
			dataType : "text",
			success : function(data) {
				if(data=="success"){
					alert("删除成功!");
					loadSysUsers();
				}else{
					alert("删除失败!请重试!");
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
	            /*弹出jqXHR对象的信息*/
	            alert(jqXHR.responseText);
	            alert(jqXHR.status);
	            alert(jqXHR.readyState);
	            alert(jqXHR.statusText);
	            /*弹出其他两个参数的信息*/
	            alert(textStatus);
	            alert(errorThrown);
	        }
		});
	}
	
	//修改用户
	function modifySysUser(userid){
		
		<%-- $.ajax({
			type : "POST",
			data: {userid},
			url : "<%=basePath %>sysUser/modifySysUser",
			dataType : "json",
			success : function(data) {
				
			}
		}); --%>
	}
	
</script>
</html>