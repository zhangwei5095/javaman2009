<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="org.ndot.spring25.scop.web.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Spring Bean Scop Test</title>
	</head>

	<body>
		<table align="center" border="1">
			<tr>
				<td align="left" colspan="2" bgcolor="yellow">
					Requst-scop ��Ϣ��
				</td>
			</tr>
			<tr>
				<td>
					�û�����
				</td>
				<td>
					${reqInfo.loginName }
				</td>
			</tr>
			<tr>
				<td>
					�û���ɫ��
				</td>
				<td>
					${reqInfo.loginRole }
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2" bgcolor="yellow">
					Session-scop ��Ϣ��
				</td>
			</tr>
			<tr>
				<td>
					�û�����
				</td>
				<td>
					${sessionInfo.loginName }
				</td>
			</tr>
			<tr>
				<td>
					�û���ɫ��
				</td>
				<td>
					${sessionInfo.loginRole }
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2" bgcolor="yellow">
					GlobalSessionInfo-scop ��Ϣ��
				</td>
			</tr>
			<tr>
				<td>
					�û�����
				</td>
				<td>
					${globalSessionInfo.loginName }
				</td>
			</tr>
			<tr>
				<td>
					�û���ɫ��
				</td>
				<td>
					${globalSessionInfo.loginRole }
				</td>
			</tr>
			<tr>

				<td colspan="2" align="right">
					<a href="<%=request.getContextPath()%>/change">����Request</a>
				</td>
			</tr>
		</table>

		<br>
	</body>
</html>
