/*
�ۺ�ǰ�� ajax �ű�����
 */

function createXMLHttpRequest() {
	var xmlHttpReq;
	//����XMLHTTP����   
	if (window.ActiveXObject) { // IE��//��������֧��window.ActiveXObject����   
		xmlHttpReq = new ActiveXObject("MSXML2.XMLHTTP.3.0");
		var MSXML = ['MSXML2.XMLHTTP.5.0', 'MSXML2.XMLHTTP.4.0',
				'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];
		try {
			xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
			  } catch (e) {
			}
		}

	} else if (window.XMLHttpRequest) { // Mozilla, Safari, ...   
		xmlHttpReq = new XMLHttpRequest();
	}

	return xmlHttpReq;
}

function ajaxSubmit(path, id) {
	var xmlHttpReq = createXMLHttpRequest();
	var state = document.getElementById(id + 'state');
	var operate = document.getElementById(id + 'operate');
	var type;
	var tem = '����';
	if (tem.indexOf(state.innerHTML) >= 0) {
		type = 'stop';
	} else {
		type = 'start';
	}
	var url = path + "/channelControl.do?rand=" + Math.random() + "&id=" + id
			+ "&type=" + type;
	xmlHttpReq.open("GET", url, true);
	//xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');    
	xmlHttpReq.onreadystatechange = function() {
		if (xmlHttpReq.readyState == 4) {
			if (xmlHttpReq.status == 200) {
				// ���¶�Ӧ�� HTML Ԫ��������ʾ������   
				state.innerHTML = xmlHttpReq.responseText;
				if ('ֹͣ' == xmlHttpReq.responseText) {
					operate.value = '����';
					Ext.MessageBox.alert("IPS-�ۺ�ǰ�������������ƽ̨", "������� ֹͣ �ɹ�����");
				} else {
					operate.value = 'ֹͣ';
				Ext.MessageBox.alert("IPS-�ۺ�ǰ�������������ƽ̨", "������� �ɹ� ��������");
				}
			}
		}
	}
	
	xmlHttpReq.send(null);
	  
	/*  
	ͬ���������ǣ����ε�����xmlHttpReq.onreadystatechange = showResult;ͬʱxmlHttpReq.open("GET",url,false);  
	����ֱ���� http_request.send(null);�����ý��  
	 var returntxt=unescape(http_request.responseText);  
	
	post ���ύ����  
	xmlHttpReq.open("POST",url,true);  
	xmlHttpReq.send("��������Ҫ���Ĳ���"); //eg:rand=" + Math.random() + "&id="+id+"&flag="+flag  
	 */
}

function commonConfirm(message){
	
     if( Ext.MessageBox.confirm('IPS-�ۺ�ǰ�������������ƽ̨',"��ȷ��Ҫ���� ��"+message +"�� ������?")){
     	return true;
     }
     else{
        return false;
     }
}

function textAnima_color2(id, n) {
	el = document.all.item(id, n);
	charColor = el.getAttribute("SET_COLOR");
	cur = el.getAttribute("CURRENT_CHAR");
	if (cur == "")
		cur = 0;
	getHTML = el.getAttribute("SET_TEXT");
	if (getHTML == "") {
		getHTML = el.innerText;
		el.setAttribute("SET_TEXT", getHTML);
	}
	bef_t = getHTML.substring(0, cur);;
	aft_t = getHTML.substring(eval(cur) + 1, getHTML.length);
	cur_t = getHTML.substr(cur, 1);
	cur_t = cur_t.fontcolor(charColor);
	el.innerHTML = bef_t + cur_t + aft_t;
	cur++;
	if (cur > getHTML.length)
		cur = 0;
	el.setAttribute("CURRENT_CHAR", cur);
	getT = el.getAttribute("SET_TIME");
	if (getT == "")
		getT = 100;
	setTimeout("textAnima_color2('" + id + "'," + n + ")", eval(getT));
}
function init() {
	id = "ANIMA_TEXT_COLOR2";
	len = document.all.item(id).length;
	if (!len)
		len = 1;
	for (var i = 0; i < len; i++) {
		textAnima_color2(id, i);
	}
}

Ext.onReady(function(){  
     Ext.MessageBox.alert('IPS-�ۺ�ǰ�������������ƽ̨', '��ӭ�����롾�ۺ�ǰ�������������ƽ̨��! ','Modal:false');    
});