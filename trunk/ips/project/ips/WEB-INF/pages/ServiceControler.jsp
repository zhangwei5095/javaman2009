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

		<title>�ۺ�ǰ�������������ƽ̨</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ips.js"></script>
	</head>

	<body>
		<table align="center"  width="500">
		  <tr align="center">
		  <tb align="center">
		  <h2 align="center">
		  �ۺ�ǰ�������������ƽ̨
		  </h2>
		  </tb>
		  </tr>
		  <tr>
		  <tb>
		<hr width="500" size="2">
		  </tb>
		  </tr>
		</table>

		<table width="700" align="center">
		<tr align="center"><td>��������</td><td>��������</td><td>�����˿�</td><td>����״̬</td><td>��  ��</td></tr>
		  <logic:iterate id="ipsChannel" name="ipsChannels">				   
                 <tr>
                 <td><bean:write name="ipsChannel" property="channelId"/></td>
                  <td><bean:write name="ipsChannel" property="name"/></td>
                   <td><bean:write name="ipsChannel" property="port"/></td>
                   <logic:equal name="ipsChannel" property="stop"  value="true" >
                   <td>ֹͣ</td>
                   <td><html:button value="����" onclick="startChannel("<bean:write name="ipsChannel" property="channelId"/>")" ></html:button></td>
                   </logic:equal>
                     <logic:notEqual name="ipsChannel" property="stop"  value="true" >
                   <td>����</td>
                   <td><html:button value="ֹͣ" onclick="stopChannel("<bean:write name="ipsChannel" property="channelId"/>")" ></html:button></td>
                 </logic:notEqual>
                 </tr>
		 </logic:iterate>
		</table>

	</body>
</html>
