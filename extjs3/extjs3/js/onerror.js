/**
 * ������Ƿ���ʾ��׼�Ĵ�����Ϣ��ȡ���� onerror �ķ���ֵ���������ֵΪ false�����ڿ���̨ (JavaScript console)
 * ����ʾ������Ϣ����֮�򲻻ᡣ
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
	// ����һ�������ڵ� �������Ա�������󣬱�js�¼����Ʋ���
	adddlert("Welcome guest!")
}

function trycatch() {
	try {
		// ����һ�������ڵ� �������Ա�������󣬱��쳣���Ʋ���
		adddlert("Welcome guest!")
	} catch (err) {
		alert(err.description )
	}

}

function aaa(){
	alert("you will throw error");
	throw "error";
}


