var xmlHttpReq = false;

    // Mozilla/Safari
    if (window.XMLHttpRequest) {
       xmlHttpReq = new XMLHttpRequest();
    }
    // IE
    else if (window.ActiveXObject) {
        xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }	

function doOnreadystatechange(){
	 if (xmlHttpReq.readyState == 4) {
            alert("rspMsg:"+xmlHttpReq.responseText);
        }else{
        	alert("readyState is: "+xmlHttpReq.readyState);
        }
}


function doRequest(){
	
	var strURL="ajax.ndot";
    xmlHttpReq.open('POST', strURL, true);
    xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlHttpReq.onreadystatechange = doOnreadystatechange;
    xmlHttpReq.send("NDot ajax request message .......");	
}


function doAbort(){
	var strURL="ajax.ndot";
    xmlHttpReq.open('POST', strURL, true);
    xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlHttpReq.onreadystatechange = doOnreadystatechange;
    xmlHttpReq.send("NDot ajax request message .......");
	xmlHttpReq.abort();
	alert("abort have done!")
}

var intrvalHandel=false;
function doSetInterval(){
	window.setInterval(showTime,2000);
}
function doClearInterval(){
	window.clearInterval(intrvalHandel);
	alert("You have clear Intervale......");
}
function showTime(){
	alert(new Date());
}
	