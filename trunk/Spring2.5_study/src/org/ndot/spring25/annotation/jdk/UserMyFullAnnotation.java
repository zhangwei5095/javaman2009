package org.ndot.spring25.annotation.jdk;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�StuJ2SE
 * 
 * �ļ����� UserMyFullAnnotation.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-10
 * 
 */
@MyAnnotation
public class UserMyFullAnnotation {
	private String message = "��";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@MyFullAnnotation(age = 1, name = "����С������Ƶѧϰ��", tel = { "151****3934",
			"150****1277" }, color = MyFullAnnotation.Color.RED, de = @Deprecated, exceptons = { RuntimeException.class })
	public void checkAnnotation() {
		System.out.println("��л " + getMessage() + " �տ�<<С����ѧ��ϵ����Ƶ>>������������");
	}
}
