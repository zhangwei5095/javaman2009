package org.ndot.spring25.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� JDKProxyFactory.java
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
 * ����ʱ��: 2009-8-29
 * 
 */
public class JDKProxyFactory implements InvocationHandler {
	// Ҫ�����ԭʼ����
	private Object objOriginal;

	public JDKProxyFactory() {

	}

	public Object createProxyInstance(Object objOriginal) {
		this.objOriginal = objOriginal;
		return Proxy.newProxyInstance(

		this.objOriginal.getClass().getClassLoader(),

		this.objOriginal.getClass().getInterfaces(),

		this);
	}


	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result;
		if (method.getName().endsWith("sayHello")) {
			// ��������֮ǰ
			doBefore();
			// ����ԭʼ����ķ���
			result = method.invoke(this.objOriginal, args);
			// ��������֮��
			doAfter();

		} else {
			result = method.invoke(this.objOriginal, args);
		}
		return result;
	}

	private void doBefore() {
		System.out.println("before method invoke!");
	}

	private void doAfter() {
		System.out.println("after method invoke!");
	}

	public static void main(String[] args) {
		HelloImp helloImp = new HelloImp();
		JDKProxyFactory pf = new JDKProxyFactory();
		Hello h = (Hello) pf.createProxyInstance(helloImp);
		h.sayHello("NDot");

	}
}
