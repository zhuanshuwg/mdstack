<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%> 

<c:set value="${pageContext.request.contextPath}" var="ctx" scope="session"/>
<c:set value="机器数据分析平台" var="title" scope="request"/>

<script type="text/javascript">
	var ctx = '${ctx}';
</script>