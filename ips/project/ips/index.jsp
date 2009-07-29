<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>综合前置渠道服务控制平台</title>
	</head>
	<body>
		<jsp:forward page="/serviceControler.do"></jsp:forward>
	</body>
</html>