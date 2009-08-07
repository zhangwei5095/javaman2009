<html>
<body>

<h2>Your employee would like to go on vacation</h2>
<form action="${form.action}" method="POST" enctype="multipart/form-data">
Number of days: ${number_of_days}<br/>
<hr>
In case you reject, please provide a reason:<br/>
<input type="textarea" name="reason"/><br/>
<#list outcome.values as transition>
    <input type="submit" name="outcome" value="${transition}">
</#list>
</form>
</body>
</html>