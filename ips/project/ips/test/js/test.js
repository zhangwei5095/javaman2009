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