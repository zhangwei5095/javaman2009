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
					Ext.MessageBox.alert("IPS-综合前置渠道服务控制平台", "服务监听 停止 成功：）");
				} else {
					operate.value = '停止';
				Ext.MessageBox.alert("IPS-综合前置渠道服务控制平台", "服务监听 成功 启动：）");
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

function commonConfirm(message){
	
     if( Ext.MessageBox.confirm('IPS-综合前置渠道服务控制平台',"您确认要进行 【"+message +"】 操作吗?")){
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
     Ext.MessageBox.alert('IPS-综合前置渠道服务控制平台', '欢迎您进入【综合前置渠道服务控制平台】! ','Modal:false');    
});