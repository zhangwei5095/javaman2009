<%@ page language="java" import="java.util.*;" pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/test34.js" ></script>
	<body onload="grid3(grid_container)">
		<div id="grid_container" style="width:700px;height:300px">
</div>
<br/><br/>
<input type="button" value="试试 页面跳转" 
onclick="mygrid3.gotoPage(3)" />
	</body>
</html>
