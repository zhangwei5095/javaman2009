package org.ndot.spring25.annotation.jdk;


import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�StuJ2SE
 * 
 * �ļ����� MyAnnotation.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-10
 * 
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {}
