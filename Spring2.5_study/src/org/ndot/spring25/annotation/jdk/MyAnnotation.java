package org.ndot.spring25.annotation.jdk;


import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：StuJ2SE
 * 
 * 文件名： MyAnnotation.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-10
 * 
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {}
