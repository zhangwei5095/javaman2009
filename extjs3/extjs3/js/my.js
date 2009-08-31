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
            alert(xmlHttpReq.responseText);
        }
}


function doRequest(strURL){
	if(strURL=="")
	strURL="ajax.ndot";
    xmlHttpReq.open('POST', strURL, true);
    xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlHttpReq.onreadystatechange = doOnreadystatechange;
    xmlHttpReq.send("NDot ajax request message .......");	
}