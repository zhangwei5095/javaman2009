<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/ext/resources/css/ext-all.css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/ext/ext-all.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/test/js/test.js"></script>
	</head>
	<body>
		<input type="button" value="alert" onclick="extjsAlert();">
		<input type="checkbox" readonly="true" onselect="true">
		<script type="text/javascript">
		

	</script>
		<input id="mycheck" type="checkbox"  onclick="extjsAlert();">
	</body>
</html>
