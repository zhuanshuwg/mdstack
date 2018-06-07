<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="/WEB-INF/pages/common/mytags.jsp"%>
<title>${title}</title>
<t:base ctx="${ctx}" type="bootstrap.css,icons.css,validform.css,main.css"></t:base>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="//cdnjs.bootcss.com/ajax/libs/html5shiv/3.6.2/html5shiv.js"></script>
    <![endif]-->
	<style type="text/css">
		body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 500px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
	</style>
</head>

<body style="min-width: 1152px;">
	<div class="container">

      <form class="form-signin">
        <h3 class="form-signin-heading" >糟糕，操作发生错误</h3>
        
        <label class="checkbox">
         	 请选择您要进行的下一步操作：
        </label>
        <ul style="font-size: 12px;">
        	<li>自己动手转到 <a href="${ctx}/home">机器数据分析平台</a> 首页</li>
        	<li>单击<a href="javascript:history.back(1)">后退</a>按钮，尝试其他链接。</li>
        </ul>
        	
      </form>

    </div> <!-- /container -->
</body>
</html>