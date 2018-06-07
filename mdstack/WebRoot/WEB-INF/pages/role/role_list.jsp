<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>角色管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${ctx}/plug-in/public/jquery/jquery-3.3.1.js"></script>
	<script src="${ctx}/plug-in/public/layer-v3.1.1/layer/layer.js"></script>
	<script src="${ctx}/plug-in/private/role/js/role_list.js"></script>
	<script src="${ctx}/plug-in/public/paging/js/paging.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	
	<link href="${ctx}/plug-in/private/role/css/role_list.css" rel="stylesheet">
    
  </head>
  <body>
  		<!-- 页面 -->
  		<div>
  			<!-- 标题 -->
  			<div>
  				<h4>角色列表</h4>
  			</div>
  			<!-- 内容 -->
  			<div class="role_list_table_div">
  				<form class="form-inline">
					<div class="form-group role_search_form">
						<button type="button" class="btn btn-primary btn-sm" onclick="addrole()">添加角色</button>
					</div>
				</form>
  				<table id="role_list_table" class="table table-bordered table-hover table-striped tablesorter">
  					<thead>
	                    <tr>
	                      <th>序号<i class="fa fa-sort"></i></th>
	                      <th>角色名称<i class="fa fa-sort"></i></th>
	                      <th>角色描述<i class="fa fa-sort"></i></th>
	                      <th>操作</th>
	                  </tr>
	                </thead>
	                <tbody id="roleManage_tb"></tbody>
  				</table>
  				<!-- 分页 -->
  				<div class="simplePaging" id="simplePaging"></div>
  			</div>
  			<!-- 添加用户弹窗 -->
  			<div id="roleDiv" class="roleDiv" style="display: none;">
  				<form id="roleForm">
  				<input type="hidden" id="id" class="form-control">
				  <div class="form-group">
				    <label>角色名称</label>
				    <input type="text" id="roleName" class="form-control" placeholder="角色名称">
				  </div>
				  <div class="form-group">
				    <label>角色描述</label>
				    <input type="text" id="descrption" class="form-control" placeholder="角色描述">
				  </div>
				</form>
  			</div>
  			<!-- 角色菜单权限弹窗 -->
  			<div id="role_menu" class="role_menu"></div>
  	</div>
  </body>
</html>
