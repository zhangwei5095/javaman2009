<%@page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
		<title>填写申请单</title>
	</head>
	<body>
		<form action="leave?family168=apply" method="POST">
		  <table width="300" border=1>
			<tr>
			  <td><label>申请人</label></td>
			  <td><input type="text" value="Kayzhan" name="applyName"></td>
			</tr>
			<tr>
			  <td><label>职位</label></td>
			  <td><input type="text" value="经理" name="position"></td>
			</tr>
			<tr>
			  <td><label>申请时间</label></td>
			  <td><input type="text" value="2009-03-09" name="applyTime"></td>
			</tr>
			<tr>
			  <td><label>请假天数</label></td>
			  <td><input type="text" value="3" name="leaveDay"></td>
			</tr>
			<tr>
			  <td><label>请假原因</label></td>
			  <td><textarea name="content" rows=3 cols=200>病假</textarea></td>
			</tr>
			<tr>
			  <td><input type="submit" value="申请"></td>
			  <td><input type="reset" value="取消"></td>
			</tr>
		  </table>
		</form>
	</body>
</html>