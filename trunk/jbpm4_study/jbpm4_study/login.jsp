<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html>
	<head>
		<title>JSP for LoginForm form</title>
	</head>
	<body>
		<html:form action="/processManager.do">
			
			userName : <html:text property="userName" />
			<br />
			passWord : <html:password property="passWord" />
			<br />
			role :
			<html:select property="role">
				<html:option value="common"></html:option>
				<html:option value="manager"></html:option>
				<html:option value="boss"></html:option>
			</html:select>
			<br />
			<html:submit />
			<html:cancel />
		</html:form>
	</body>
</html>

