<%@ page language="java" import="java.util.*;" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>js Test</title>

		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<br />
		<br />
		<br />

		<table align="left" border="1">
<tr>
				<td >
					序号
				</td>
				<td >
					功能说明
				</td>
				<td align="right">
					页面链接
				</td>

			</tr>
			<tr>
				<td >
					1>>
				</td>
				<td >
					Ajax基本用法测试
				</td>
				<td align="right">
					<a href="<%=request.getContextPath()%>/pages/myAjax.jsp">myAjax.jsp</a>
				</td>

			</tr>
			<tr>
			<td >
					2>>
				</td>
				<td >
					Html 对 js 加载顺序
				</td>
				<td align="right">
					<a href="<%=request.getContextPath()%>/pages/loadOrder.jsp">loadOrder.jsp</a>
				</td>

			</tr>
			<tr>
			<td >
					3>>
				</td>
				<td >
					js 错误处理机制
				</td>
				<td align="right">
					<a href="<%=request.getContextPath()%>/pages/onerror.jsp">onerror.jsp</a>
				</td>

			</tr>
		</table>
	</body>
</html>
