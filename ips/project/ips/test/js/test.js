Ext.onReady(




function(){
	checkbox.Attributes.Add("disabled","disabled")
     Ext.MessageBox.alert("提示框", "这是一个提示框");
}
); 

function extjsAlert(){
	var check = document.getElementById("mycheck");
		alert("v="+check.value);
		check.Attributes.Add("disabled","disabled");
		alert("vvvv="+check.value);
     Ext.MessageBox.alert("提示框", "这是一个提示框");
}
