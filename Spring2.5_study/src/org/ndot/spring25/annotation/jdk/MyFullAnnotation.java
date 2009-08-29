package org.ndot.spring25.annotation.jdk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�StuJ2SE
 * 
 * �ļ����� MyFullAnnotation.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-10
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.CONSTRUCTOR,
		ElementType.ANNOTATION_TYPE, ElementType.TYPE })
public @interface MyFullAnnotation {

	// ��Ƕ��ö������
	public static enum Color {
		BLACK, RED, WHITE, YELLOW
	};

	// ����ķ���������annotation�ĳ�Ա

	public Color color();

	public String name() default "С����ѧ��";

	public int age() default 1;

	public String[] tel();

	public Class<? extends RuntimeException>[] exceptons();

	public Deprecated de();

}
