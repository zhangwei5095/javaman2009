<%@ page language="java" import="java.util.*;" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>js Test</title>

		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/onerror.js"></script>
	</head>

	<body>
		<input type="button" value="【onerror】" onclick="message()">
		<br>
		<input type="button" value="【try-catch】" onclick="trycatch()">
		<br>
		<input type="button" value="【Throw】" onclick="aaa()">
	</body>
</html>
