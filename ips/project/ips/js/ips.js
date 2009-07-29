
/*
综合前置 ajax 脚本集合
*/

function createXMLHttpRequest(){
   var xmlHttp;
　　if (window.ActiveXObject) {
　　xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
　　}
　　else if (window.XMLHttpRequest) {
　　xmlHttp = new XMLHttpRequest();
　　}
   return xmlHttp;
}
function startChannel(channelid){
	alert("停止 渠道代碼為 "+channelid+"的服务监听......");
	 var xmlHttp = createXMLHttpRequest();
	 xmlHttp.onreadystatechange=function(){
	 
	 
	 }
	 xmlHttp.open();
}
function stopChannel(channelid){
	alert("停止 渠道代碼為 "+channelid+"的服务监听......");
}