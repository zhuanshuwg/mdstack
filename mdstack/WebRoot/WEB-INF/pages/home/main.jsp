<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>首页</title>
<script src="${ctx}/plug-in/public/jquery/jquery-3.3.1.js"></script>
<script src="${ctx}/plug-in/home/js/main.js"></script>
<script src="${ctx}/plug-in/public/layer-v3.1.1/layer/layer.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="${ctx}/plug-in/public/layui-v2.3.0-rc1/layui/css/layui.css">
<link rel="stylesheet" href="${ctx}/plug-in/home/css/main.css">
</head>
	<body>
	  <!-- header start -->
	  <%-- <iframe name="iframe_header" class="iframe_header" id="iframe_header" src="${ctx}/login/header" frameborder="0">
	  </iframe> 
	  <iframe name="iframe_left" class="iframe_left" id="iframe_left" src="${ctx}/login/left" frameborder="0">
	  </iframe> 
	  --%>
	  <div id="header" style="width:100%; height:60px; background-color: black;"><jsp:include page='/WEB-INF/pages/home/header.jsp'/></div>
	  <div id="left" style="width: 10%; background-color: black; float: left;"><jsp:include page='/WEB-INF/pages/home/left.jsp'/></div>
	  <div id="weilcome" class="weilcome" style="width: 90%; float: left;">
	  	<iframe id="main_iframe" name="weilcome" style="width: 100%; src="${ctx}/login/welcome" frameborder="0"></iframe>
	  </div>
  </body>
</html>