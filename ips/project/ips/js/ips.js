
/*
�ۺ�ǰ�� ajax �ű�����
*/

function createXMLHttpRequest(){
   var xmlHttp;
����if (window.ActiveXObject) {
����xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
����}
����else if (window.XMLHttpRequest) {
����xmlHttp = new XMLHttpRequest();
����}
   return xmlHttp;
}
function startChannel(channelid){
	alert("ֹͣ �������a�� "+channelid+"�ķ������......");
	 var xmlHttp = createXMLHttpRequest();
	 xmlHttp.onreadystatechange=function(){
	 
	 
	 }
	 xmlHttp.open();
}
function stopChannel(channelid){
	alert("ֹͣ �������a�� "+channelid+"�ķ������......");
}