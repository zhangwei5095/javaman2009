<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="org.ndot.ips.comm.IPSReportChannel;"%>
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

		<title>综合前置渠道服务控制平台</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/ips.js"></script>
	</head>

	<body>
		<table align="center" width="700">
			<tr align="center" height="30">
				<td></td>
			</tr>
			<tr align="center">
				<td align="center">
					<h2 align="center">
						综合前置渠道服务控制平台
					</h2>
				</td>
			</tr>
			<tr align="center">
				<td align="center">
					<hr width="500" size="2">
				</td>
			</tr>
		</table>

		<table width="700" align="center" border="1">
			<tr align="center">
				<td align="center">
					渠道编码
				</td>
				<td align="center">
					渠道名称
				</td>
				<td align="center">
					监听端口
				</td>
				<td align="center">
					服务状态
				</td>
				<td align="center">
					操 作
				</td>
			</tr>
			<logic:iterate id="ipsChannel" name="ipsChannels">
				<tr>
					<td align="center">
						<bean:write name="ipsChannel" property="channelId" />
					</td>
					<td align="center">
						<bean:write name="ipsChannel" property="name" />
					</td>
					<td align="center">
						<bean:write name="ipsChannel" property="port" />
					</td>
					<td id="<bean:write name="ipsChannel" property="channelId"/>state"
						align="center">
						停止
					</td>
					<td align="center">
						<input id="<bean:write name="ipsChannel" property="channelId"/>operate" type="button" value="启动"
							onclick="ajaxSubmit('<%=request.getContextPath()%>','<bean:write name="ipsChannel" property="channelId"/>')">
					</td>
					
				</tr>
			</logic:iterate>
		</table>

	</body>
</html>
