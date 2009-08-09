<%@ page language="java" import="java.util.*;" pageEncoding="GBK"%>
<%@ page="org.jbpm.api.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>工作流管理控制平台</title>
	</head>
	<link rel="stylesheet" type="text/css"
		href="<%=path%>/ext/resources/css/ext-all.css">
	<script type="text/javascript" src="<%=path%>/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=path%>/ext/ext-all.js"></script>
	<script type="text/javascript" src="<%=path%>/js/jbpm4.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">

	<body onload="init()">
		<table cellspacing="4" width="100%" border="0">
			<tr>
				<td colspan="2">
					<a href="http://www.apache.org/"> <img border="0"
							alt="The Apache Software Foundation" align="left"
							src="/manager/images/asf-logo.gif"> </a>
					<a href="http://tomcat.apache.org/"> <img border="0"
							alt="The Tomcat Servlet/JSP Container" align="right"
							src="/manager/images/tomcat.gif"> </a>
				</td>
			</tr>
		</table>
		<hr size="1" noshade="noshade">
		<table align="center" width="700">
			<tr align="center" height="30">
				<td></td>
			</tr>
			<tr align="center">
				<td align="center">
					<h1 align="center">
						工作流管理控制平台
					</h1>
				</td>
			</tr>
			<tr align="center">
				<td align="center">
					<hr width="500" size="2">
				</td>
			</tr>
		</table>
		<table border="1" cellspacing="0" cellpadding="1" align="center">
			<tr>
				<td class="row" width="15%" bgcolor="#FFDC75">
					<span
						style="font-size: 14pt; color = blue; font-family: sans-serif, Tahoma, Arial"
						id="ANIMA_TEXT_COLOR2" SET_COLOR="red" SET_TIME="500" CURRENT_CHAR
						SET_TEXT>系 统 消 息 </span>
				</td>
				<td class="row">
					<marquee direction=left>
						欢迎您使用-【小蚂蚁学堂-工作流管理控制平台】 技术支持：QQ 289272063
					</marquee>
				</td>
			</tr>
		</table>
		<br>

		<br>

		<hr size="1" noshade="noshade">
		<center>
			<font size="-1" color="#525D76"> <em>Copyright &copy;
					2009-2015, Samll ants school - Author:Sun Jincheng</em> </font>
		</center>
	</body>
</html>
