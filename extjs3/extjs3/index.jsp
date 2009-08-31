<%@ page language="java" import="java.util.*;" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>js Test</title>

		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/my.js"></script>
	</head>

	<body>
		<br />
		<br />
		<br />

		<table align="left" border="1" >

			<tr>
				<td>
					1
				</td>
				<td align="right">
					<input type="button" value="链路测试" onclick="doRequest()" />
				</td>
			</tr>
			<tr>
				<td>
					2
				</td>
				<td align="right">
					<input type="button" value="Abort()" onclick="doAbort()" />
				</td>
			</tr>
			<tr>
				<td>
					3
				</td>
				<td align="right">
					<input type="button" value="setInterval()"
						onclick="doSetInterval()" />
					||
					<input type="button" value="clearInterval()"
						onclick="doClearInterval()" />
				</td>
			</tr>

		</table>
	</body>
</html>
