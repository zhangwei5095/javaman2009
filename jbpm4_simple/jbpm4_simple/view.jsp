<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>填写申请单</title>
	</head>
	<body>
	
		  <table width="300" border=1>
			<tr>
			  <td><label>申请人</label></td>
			  <td>${map.name }</td>
			</tr>
			<tr>
			  <td><label>职位</label></td>
			  <td>${map.position }</td>
			</tr>
			<tr>
			  <td><label>申请时间</label></td>
			  <td>${map.time }</td>
			</tr>
			<tr>
			  <td><label>请假天数</label></td>
			  <td>${map.leaveDay }</td>
			</tr>
			<tr>
			  <td><label>请假内容</label></td>
			  <td>${map.content }</td>
			</tr>
		  </table>
	</body>
</html>