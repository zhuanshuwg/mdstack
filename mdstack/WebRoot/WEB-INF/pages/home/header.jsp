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
	<script src="${ctx}/plug-in/home/js/header.js"></script>
	<link href="${ctx}/plug-in/home/css/header.css" rel="stylesheet">
  </head>
  
  <body>
    <div>
    	<div class="header_title"><h1>后台管理系统</h1></div>
    	<div class="header_info form-group">
    		<a href="javascript:void(0);">
	    		<i class="glyphicon glyphicon-user"></i>
	    		<span>***</span>
    		</a>
    		
    		<a href="javascript:void(0);">
	    		<i class="glyphicon glyphicon-pencil"></i>
	    		<span>密码修改</span>
    		</a>
    		
    		<a onclick="layout()">
	    		<i class="glyphicon glyphicon-off"></i>
	    		<span>注销登录</span>
    		</a>
    	</div>
    </div>
                                                                                                             
  </body>
</html>
