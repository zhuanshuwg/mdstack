<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'user_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${ctx}/plug-in/public/jquery/jquery-3.3.1.js"></script>
	<script src="${ctx}/plug-in/public/layer-v3.1.1/layer/layer.js"></script>
	<script src="${ctx}/plug-in/private/user/js/user_list.js"></script>
	<script src="${ctx}/plug-in/public/paging/js/paging.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	
	<link href="${ctx}/plug-in/private/user/css/user_list.css" rel="stylesheet">
    
  </head>
  <body>
  		<!-- 页面 -->
  		<div>
  			<!-- 标题 -->
  			<div>
  				<h4>用户列表</h4>
  			</div>
  			<!-- 内容 -->
  			<div class="user_list_table_div">
  				<form class="form-inline">
					<div class="form-group user_search_form">
						<button type="button" class="btn btn-primary btn-sm" onclick="addUser()">添加用户</button>
						<label for="exampleInputName2">搜索框位置：</label>
						<input type="text" class="form-control searchinput" id="searchName">
						<button type="button" class="btn btn-success btn-sm" onclick="search();">查询</button>
					</div>
				</form>
  				<table id="user_list_table" class="table table-bordered table-hover table-striped tablesorter">
  					<thead>
	                    <tr>
	                      <th>序号<i class="fa fa-sort"></i></th>
	                      <th>账户<i class="fa fa-sort"></i></th>
	                      <th>姓名<i class="fa fa-sort"></i></th>
	                      <th>联系电话<i class="fa fa-sort"></i></th>
	                      <th>状态<i class="fa fa-sort"></i></th>
	                      <th>操作</th>
	                  </tr>
	                </thead>
	                <tbody id="userManage_tb"></tbody>
  				</table>
  				<!-- 分页 -->
  				<div class="simplePaging" id="simplePaging"></div>
  			</div>
  			<!-- 添加用户弹窗 -->
  			<div id="addUserDiv" class="addUserDiv" style="display: none;">
  				<form id="addUserForm">
  				<input type="hidden" id="userId" class="form-control">
				  <div class="form-group">
				    <label>账号</label>
				    <input type="text" id="userName" class="form-control" placeholder="账号">
				  </div>
				  <div class="form-group">
				    <label for="exampleInputPassword1">密码</label>
				    <input type="password" id="userPwd" class="form-control" placeholder="密码">
				  </div>
				  <div class="form-group">
				    <label>姓名</label>
				    <input type="text" id="realName" class="form-control" placeholder="姓名">
				  </div>
				  <div class="form-group">
				    <label>联系电话</label>
				    <input type="number" id="phone" class="form-control" placeholder="phone">
				  </div>
				</form>
  			</div>
  			<!-- 设置用户角色弹窗 -->
  			<div id="user_role_alert" class="user_role_alert"></div>
  	</div>
  </body>
</html>
