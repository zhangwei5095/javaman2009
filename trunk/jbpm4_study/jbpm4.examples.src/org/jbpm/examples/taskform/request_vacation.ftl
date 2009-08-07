<html>
<body>

<h2>How many days would you like to go on vacation?</h2>
<form action="${form.action}" method="POST" enctype="multipart/form-data">
<select name="number_of_days">
	<option value="3">3 days</option>
	<option value="5">5 days</option>
	<option value="10">10 days</option>
</select><br>

<#list outcome.values as transition>
    <input type="submit" name="outcome" value="${transition}">
</#list>

</form>
</body>
</html>