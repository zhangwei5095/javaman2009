package org.ndot.spring25.aop.annotation;

import org.springframework.stereotype.Service;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 *
 * 项目名称：Spring2.5_study
 * 
 *<P>
 *
 * 文件名： HelloWorld.java
 * 
 *<P>
 *
 * 功 能:
 * 
 *
 *<P>
 *
 *
 * 作 者: SunJincheng
 * 
 *<P>
 *
 * 创建时间: 2009-9-16
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
                //这个异常是拿来测试，可有可无   
    }  
}
