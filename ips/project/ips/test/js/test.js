Ext.onReady(




function(){
	checkbox.Attributes.Add("disabled","disabled")
     Ext.MessageBox.alert("��ʾ��", "����һ����ʾ��");
}
); 

function extjsAlert(){
	var check = document.getElementById("mycheck");
		alert("v="+check.value);
		check.Attributes.Add("disabled","disabled");
		alert("vvvv="+check.value);
     Ext.MessageBox.alert("��ʾ��", "����һ����ʾ��");
}
