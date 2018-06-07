<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh">
  <head>
    <title>带科幻风格的纯CSS3用户登录界面设计|DEMO_jQuery之家-自由分享jQuery、html5、css3的插件库</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="keywords" content="keyword1,keyword2,keyword3">
    <meta name="description" content="this is my page">
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script src='${ctx}/plug-in/public/jquery/jquery-3.3.1.js'></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/plug-in/login/css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/plug-in/login/css/default.css">
	<script src='${ctx}/plug-in/login/js/prefixfree.min.js'></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/plug-in/login/css/styles.css">
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

  </head>
  
  <body>
  <script type="text/javascript">
  if (top.location !== self.location) {
    top.location=self.location;
}
  </script>
<div id="logo"> 
  <h1 class="hogo"><i>Storm Technology</i></h1>
</div> 
<section class="stark-login">
  <form action="${ctx}/login" method="post">	
	 <div id="fade-box">
		<input type="text" name="userName" id="userName" placeholder="userName" required>
	  	<input type="password" placeholder="Password" required name="userPwd" id="userPwd">
	    <button>登录</button> 
	  </div>
   </form>
   <div class="hexagons">
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <br>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <br>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span> 
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <br>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <br>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
     <span>&#x2B22;</span>
   </div>      
</section> 
<div id="circle1">
  <div id="inner-cirlce1">
    <h2> </h2>
  </div>
</div>
<ul>
  <li></li>
  <li></li>
  <li></li>
  <li></li>
  <li></li>
</ul>
<header class="htmleaf-header">
	<h1>小程序后台管理登录 <span>最终解释权归作者所有</span></h1>
	<!-- <div class="htmleaf-links">
		<a class="htmleaf-icon icon-htmleaf-home-outline" href="http://www.htmleaf.com/" title="jQuery之家" target="_blank"><span> jQuery之家</span></a>
		<a class="htmleaf-icon icon-htmleaf-arrow-forward-outline" href="http://www.htmleaf.com/css3/ui-design/201507212272.html" title="返回下载页" target="_blank"><span> 返回下载页</span></a>
	</div> -->
</header>
</body>
</html>
