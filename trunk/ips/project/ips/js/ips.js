
/*
综合前置 ajax 脚本集合
 */

function createXMLHttpRequest() {
	var xmlHttpReq;
	//创建XMLHTTP对象   
	if (window.ActiveXObject) { // IE，//如果浏览器支持window.ActiveXObject对象   
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
	var tem = '启动';
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
				// 更新对应的 HTML 元素里面显示的内容   
				state.innerHTML = xmlHttpReq.responseText;
				if ('停止' == xmlHttpReq.responseText) {
					operate.value = '启动';
					alert("服务监听 停止 成功：）");
				} else {
					operate.value = '停止';
					alert("服务监听 成功 启动：）");
				}
			}
		}
	}
	
	xmlHttpReq.send(null);
	  
	/*  
	同步的做法是：屏蔽掉上面xmlHttpReq.onreadystatechange = showResult;同时xmlHttpReq.open("GET",url,false);  
	接着直接在 http_request.send(null);下面获得结果  
	 var returntxt=unescape(http_request.responseText);  
	
	post 的提交做法  
	xmlHttpReq.open("POST",url,true);  
	xmlHttpReq.send("这里是需要传的参数"); //eg:rand=" + Math.random() + "&id="+id+"&flag="+flag  
	 */
}
