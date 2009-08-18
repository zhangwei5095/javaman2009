<%@page contentType="text/html;charset=UTF-8" %>
<%@page import="org.jbpm.api.*" %>
<%@page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>请假审批</title>
	</head>
	<body>
		<table width=600 border=1>
	  	    <tr><td colspan="8">任务列表</td></tr>
			<tr>
				<td>ID</td>
				<td>名称</td>
				<td>分配人</td>
				<td>创建时间</td>
				<td>持续时间</td>
				<td>优先级</td>
				<td>描述</td>
				<td>操作</td>
			</tr>
			<c:forEach var="task" items="${taskList}">
			<tr>
				<td>${task.id}</td>
				<td>${task.name}</td>
				<td>${task.assignee}</td>
				<td>${task.createTime}</td>
				<td>${task.duedate}</td>
				<td>${task.priority}</td>
				<td>${task.description}</td>
				<td>
				  <c:if test="${task.assignee eq 'Lingo' || task.assignee eq 'ForgetDavi'}">
					<a href="leave?family168=view&id=${task.id }" target="_blank">审核</a>
					<a href="leave?family168=confirm&id=${task.id }&name=${task.assignee}">批准</a>
					<a href="leave?family168=dissent&id=${task.id }&name=${task.assignee}">不批准</a>
					<a href="leave?family168=reject&id=${task.id }&name=${task.assignee}">驳回</a>
				  </c:if>
				  <c:if test="${task.assignee eq 'Kayzhan'}">
					<a href="leave?family168=reApply&id=${task.id }" target="_blank">重新申请</a>
				  </c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>