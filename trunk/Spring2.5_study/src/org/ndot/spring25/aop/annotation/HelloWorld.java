package org.ndot.spring25.aop.annotation;

import org.springframework.stereotype.Service;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 *
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 *
 * �ļ����� HelloWorld.java
 * 
 *<P>
 *
 * �� ��:
 * 
 *
 *<P>
 *
 *
 * �� ��: SunJincheng
 * 
 *<P>
 *
 * ����ʱ��: 2009-9-16
 * 
 */
@Service
public class HelloWorld {
	public void sayHello(String helloworld) {   
        System.out.println(helloworld);    
    }  
	public void sayHelloWithException(String helloworld) {   
        System.out.println(helloworld);   
        throw new RuntimeException();   
                //����쳣���������ԣ����п���   
    }  
}
