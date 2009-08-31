/**
 * 浏览器是否显示标准的错误消息，取决于 onerror 的返回值。如果返回值为 false，则在控制台 (JavaScript console)
 * 中显示错误消息。反之则不会。
 */
onerror = handleErr
var txt = ""

function handleErr(msg, url, l) {
	txt = "There was an error on this page.\n\n"
	txt += "Error: " + msg + "\n"
	txt += "URL: " + url + "\n"
	txt += "Line: " + l + "\n\n"
	txt += "Click OK to continue.\n\n"
	alert(txt)
	// return true;
	return false;
}

function message() {
	// 调用一个不存在的 函数，以便产生错误，被js事件机制捕获。
	adddlert("Welcome guest!")
}

function trycatch() {
	try {
		// 调用一个不存在的 函数，以便产生错误，被异常机制捕获。
		adddlert("Welcome guest!")
	} catch (err) {
		alert(err.description )
	}

}

function aaa(){
	alert("you will throw error");
	throw "error";
}


