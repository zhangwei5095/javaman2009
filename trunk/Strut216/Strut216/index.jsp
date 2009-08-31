<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>

		<title>登录页面</title>

	</head>

	<body>

		<!-- 提交请求参数的表单 -->

		<form action="Login.action" method="post">

			<table align="center">

				<caption>
					<h3>
						用户登录
					</h3>
				</caption>

				<tr>

					<!-- 用户名的表单域 -->

					<td>
						用户名：
						<input type="text" name="username" />
					</td>

				</tr>

				<tr>

					<!-- 密码的表单域 -->

					<td>
						密&nbsp;&nbsp;码：
						<input type="text" name="password" />
					</td>

				</tr>

				<tr align="center">

					<td colspan="2">
						<input type="submit" value="登录" />
						<input type="reset" value="重填" />
					</td>

				</tr>

			</table>

		</form>

	</body>

</html>

