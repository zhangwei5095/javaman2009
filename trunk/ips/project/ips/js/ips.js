
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
					alert("������� ֹͣ �ɹ�����");
				} else {
					operate.value = 'ֹͣ';
					alert("������� �ɹ� ��������");
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
