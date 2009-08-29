package org.ndot.spring25.annotation.jdk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：StuJ2SE
 * 
 * 文件名： MyFullAnnotation.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-10
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.CONSTRUCTOR,
		ElementType.ANNOTATION_TYPE, ElementType.TYPE })
public @interface MyFullAnnotation {

	// 内嵌的枚举类型
	public static enum Color {
		BLACK, RED, WHITE, YELLOW
	};

	// 下面的方法定义了annotation的成员

	public Color color();

	public String name() default "小蚂蚁学堂";

	public int age() default 1;

	public String[] tel();

	public Class<? extends RuntimeException>[] exceptons();

	public Deprecated de();

}
