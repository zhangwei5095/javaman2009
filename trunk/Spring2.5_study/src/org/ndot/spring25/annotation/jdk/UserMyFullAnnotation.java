package org.ndot.spring25.annotation.jdk;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：StuJ2SE
 * 
 * 文件名： UserMyFullAnnotation.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-10
 * 
 */
@MyAnnotation
public class UserMyFullAnnotation {
	private String message = "您";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@MyFullAnnotation(age = 1, name = "国内小蚂蚁视频学习者", tel = { "151****3934",
			"150****1277" }, color = MyFullAnnotation.Color.RED, de = @Deprecated, exceptons = { RuntimeException.class })
	public void checkAnnotation() {
		System.out.println("感谢 " + getMessage() + " 收看<<小蚂蚁学堂系列视频>>．．．．．．");
	}
}
