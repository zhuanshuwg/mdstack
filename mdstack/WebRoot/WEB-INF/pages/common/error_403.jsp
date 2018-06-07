<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Exception!</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is error page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
		错误码：
		<%=request.getAttribute("javax.servlet.error.status_code")%>
		<br>
		信息：
		<%=request.getAttribute("javax.servlet.error.message")%>
		<br>
		Servlet名字：
		<%=request.getAttribute("javax.servlet.error.servlet_name")%>
		<br>
		请求URI：
		<%=request.getAttribute("javax.servlet.error.request_uri")%>
		<br>
		异常类型：
		<%=request.getAttribute("javax.servlet.error.exception_type")%>
		<br>
		异常：
		<%=request.getAttribute("javax.servlet.error.exception")%>
		<br>

		<!--为了防止页面过小而无法显示-->
		<!--1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111-->
		<!--1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111-->
		<!--1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111-->
		<!--1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111-->
		<!--1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111-->

	</body>
</html>
