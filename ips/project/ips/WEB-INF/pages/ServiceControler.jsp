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
	session.setAttribute("row", "1");
	String row = (String) session.getAttribute("row");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>�ۺ�ǰ�������������ƽ̨</title>
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/styles.css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/ips.js"></script>
	</head>
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
						�ۺ�ǰ�������������ƽ̨
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
						SET_TEXT>ϵ ͳ �� Ϣ </span>
				</td>
				<td class="row">
					<marquee direction=left>
						��ӭ��ʹ��-��С����ѧ��-�ۺ�ǰ�������������ƽ̨�� ����֧�֣�QQ 289272063
					</marquee>
				</td>
			</tr>
		</table>
		<br>
		<table width="700" align="center" border="1">
			<tr align="center">
				<td align="left" colspan="2" class="title">
					������������
				</td>
				<td align="right" colspan="3" class="title-right">
					<a
						href="<%=request.getContextPath()%>/serviceControler.do?reLoad=reload"
						onclick="return commonConfirm('���¼�������')">���¼�������</a>
				</td>
			</tr>
			<tr align="center">
				<td align="center" class="header">
					��������
				</td>
				<td align="center" class="header">
					��������
				</td>
				<td align="center" class="header">
					�����˿�
				</td>
				<td align="center" class="header">
					����״̬
				</td>
				<td align="center" class="header">
					�� ��
				</td>
			</tr>
			<logic:iterate id="ipsChannel" name="ipsChannels">
				<logic:equal name="row" value="0">
					<tr>
						<td align="center" bgcolor="#C3F3C3" class="row-center">
							<bean:write name="ipsChannel" property="channelId" />
						</td>
						<td align="center" bgcolor="#C3F3C3" class="row-center">
							<bean:write name="ipsChannel" property="name" />
						</td>
						<td align="center" bgcolor="#C3F3C3" class="row-center">
							<bean:write name="ipsChannel" property="port" />
						</td>
						<logic:equal name="ipsChannel" property="stop" value="true">
							<td
								id="<bean:write name="ipsChannel" property="channelId"/>state"
								align="center" bgcolor="#C3F3C3" class="row-center">
								ֹͣ
							</td>
							<td align="center" bgcolor="#C3F3C3" class="row-center">
								<input
									id="<bean:write name="ipsChannel" property="channelId"/>operate"
									type="button" value="����"
									onclick="ajaxSubmit('<%=request.getContextPath()%>','<bean:write name="ipsChannel" property="channelId"/>')">
							</td>
						</logic:equal>
						<logic:notEqual name="ipsChannel" property="stop" value="true">
							<td
								id="<bean:write name="ipsChannel" property="channelId"/>state"
								align="center" bgcolor="#C3F3C3" class="row-center">
								����
							</td>
							<td align="center" bgcolor="#C3F3C3" class="row-center">
								<input
									id="<bean:write name="ipsChannel" property="channelId"/>operate"
									type="button" value="ֹͣ"
									onclick="ajaxSubmit('<%=request.getContextPath()%>','<bean:write name="ipsChannel" property="channelId"/>')">
							</td>
						</logic:notEqual>
					</tr>
				</logic:equal>
				<logic:notEqual name="row" value="0">
					<tr>
						<td align="center" class="row-center">
							<bean:write name="ipsChannel" property="channelId" />
						</td>
						<td align="center" class="row-center">
							<bean:write name="ipsChannel" property="name" />
						</td>
						<td align="center" class="row-center">
							<bean:write name="ipsChannel" property="port" />
						</td>
						<logic:equal name="ipsChannel" property="stop" value="true">
							<td
								id="<bean:write name="ipsChannel" property="channelId"/>state"
								align="center"  class="row-center">
								ֹͣ
							</td>
							<td align="center"  class="row-center">
								<input
									id="<bean:write name="ipsChannel" property="channelId"/>operate"
									type="button" value="����"
									onclick="ajaxSubmit('<%=request.getContextPath()%>','<bean:write name="ipsChannel" property="channelId"/>')">
							</td>
						</logic:equal>
						<logic:notEqual name="ipsChannel" property="stop" value="true">
							<td
								id="<bean:write name="ipsChannel" property="channelId"/>state"
								align="center"  class="row-center">
								����
							</td>
							<td align="center" class="row-center">
								<input
									id="<bean:write name="ipsChannel" property="channelId"/>operate"
									type="button" value="ֹͣ"
									onclick="ajaxSubmit('<%=request.getContextPath()%>','<bean:write name="ipsChannel" property="channelId"/>')">
							</td>
						</logic:notEqual>
					</tr>
				</logic:notEqual>
				<%
					row = String.valueOf((Integer.parseInt((row + 1)) % 2));
							session.setAttribute("row", "0");
				%>
			</logic:iterate>
		</table>
		<br>

		<hr size="1" noshade="noshade">
		<center>
			<font size="-1" color="#525D76"> <em>Copyright &copy;
					2009-2015, Samll ants school - Author:Sun Jincheng</em> </font>
		</center>
	</body>
</html>
