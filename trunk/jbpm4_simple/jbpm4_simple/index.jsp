<%@page contentType="text/html;charset=UTF-8" %>
<%@page import="org.jbpm.api.*" %>
<%@page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>jBPM4视频教程基本应用系列</title>
	</head>
	<body>
	    <form action="leave?family168=deploy" method="POST" enctype="multipart/form-data">
	    	<input type="file" name="upload">
	    	<input type="submit" value="发布">
	    </form>
		<ul>
			<li><a href="leave?family168=leave">请假</a></li>
			<li><a href="leave?family168=getTask&name=Kayzhan" target="_blank">请假任务</a></li>
			<li><a href="leave?family168=getTask&name=Lingo" target="_blank">经理的任务列表</a></li>
			<li><a href="leave?family168=getTask&name=ForgetDavi" target="_blank">老板的任务列表</a></li>
		</ul>
		<table width=480 border=1>
	  	    <tr><td colspan="5">流程定义</td></tr>
			<tr>
				<td>ID</td>
				<td>Key</td>
				<td>名称</td>
				<td>版本</td>
				<td>操作</td>
			</tr>
			<c:forEach var="process" items="${process}">
			<tr>
				<td>${process.id}</td>
				<td>${process.key}</td>
				<td>${process.name}</td>
				<td>${process.version}</td>
				<td><a href="leave?family168=query&id=${process.id }">查看</a></td>
			</tr>
			</c:forEach>
		</table>
		<br>
		<br>
		<br>
		<table width=480 border=1>
	  	    <tr><td colspan="5">流程实例</td></tr>
			<tr>
				<td>ID</td>
				<td>Key</td>
				<td>状态</td>
			</tr>
			<c:forEach var="pi" items="${pi}">
			<tr>
				<td>${pi.id}</td>
				<td>${pi.key}</td>
				<td>${pi.state}</td>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>