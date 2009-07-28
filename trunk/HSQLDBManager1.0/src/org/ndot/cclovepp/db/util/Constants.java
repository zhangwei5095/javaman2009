package org.ndot.cclovepp.db.util;

public class Constants {

	//颜色的key值
	public static final String COLOR_COMMENT = "Comment";
	public static final String COLOR_KEYWORD = "Keyword";
	public static final String COLOR_STRING = "string";
	public static final String COLOR_OBJECT = "object";
	//字体的key值
	public static final String CODE_FONT = "Font";
	//语法使用的关键字
	public static final String[] JS_SYNTAX_KEYWORD = { 
		"select", "create","insert","into","table",
		"from","where","order","group","by",
		"desc","asc","update","alert","set","integer"
		,"varchar","varchar2","schema","drop","as","primary","key"
	};
	//语法使用的内置对象
	public static final String[] JS_SYNTAX_BUILDIB_OBJECT = { 
		"Anchor","anchors","Applet","applets","Area",
		"Array","Button","Checkbox","Date","document",
		"FileUpload","Form","forms","Frame","frames",
		"Hidden","history","Image","images","Link","write"
	};
	//程序中使用的一些图片常量
	public static final String ICON_OPEN = "icon_open";
	public static final String ICON_SAVE = "icon_save";
	public static final String ICON_PINT = "icon_print";
	public static final String ICON_UNDO = "icon_undo";
	public static final String ICON_REDO = "icon_redo";
	public static final String ICON_SEARCH = "icon_search";
	public static final String ICON_PREFS = "icon_prefs";
	public static final String ICON_HELP = "icon_help";
}
