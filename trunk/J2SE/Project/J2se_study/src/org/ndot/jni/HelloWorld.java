package org.ndot.jni;
/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� helloWorld.java
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
 * ����ʱ��: 2009-10-19
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
