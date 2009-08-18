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
			  <td><input type="text" value="${map.name }" name="applyName"></td>
			</tr>
			<tr>
			  <td><label>职位</label></td>
			  <td><input type="text" value="${map.position }" name="position"></td>
			</tr>
			<tr>
			  <td><label>申请时间</label></td>
			  <td><input type="text" value="${map.time }" name="applyTime"></td>
			</tr>
			<tr>
			  <td><label>请假天数</label></td>
			  <td><input type="text" value="${map.leaveDay }" name="leaveDay"></td>
			</tr>
			<tr>
			  <td><label>请假原因</label></td>
			  <td><textarea name="${map.content }" rows=3 cols=200>病假</textarea></td>
			</tr>
			<tr>
			  <td><label>驳回人</label></td>
			  <td>${map.reject }</td>
			</tr>
			<tr>
			  <td><input type="submit" value="申请"></td>
			  <td><input type="reset" value="取消"></td>
			</tr>
		  </table>
		</form>
	</body>
</html>