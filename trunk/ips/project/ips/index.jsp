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
		<title>�ۺ�ǰ�������������ƽ̨</title>
	</head>
	<body>
		<jsp:forward page="/serviceControler.do"></jsp:forward>
	</body>
</html>