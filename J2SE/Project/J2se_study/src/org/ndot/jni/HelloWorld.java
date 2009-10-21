package org.ndot.jni;
/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： helloWorld.java
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
 * 创建时间: 2009-10-19
 * 
 */
public class HelloWorld {
	public native void SayHello();

    static
    {
        System.loadLibrary("libmydll");
    }

    public static void main(String [] argv)
    {
        HelloWorld hello = new HelloWorld();
        hello.SayHello();
    }
}
