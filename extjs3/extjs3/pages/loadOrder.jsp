<%@ page language="java" import="java.util.*;" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>js Test</title>

		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript">
	var loadMsg = "";
	function showMsg(){
           alert(loadMsg);
		}
</script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/loadOrder/js1.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/loadOrder/js2.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/loadOrder/js3.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/loadOrder/js4.js"></script>
	</head>

	<body>
		<input type="button" value="showLoadMsg" onclick="showMsg()">
	</body>
</html>
