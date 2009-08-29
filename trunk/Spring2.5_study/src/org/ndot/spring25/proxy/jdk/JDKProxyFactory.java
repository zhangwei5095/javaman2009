package org.ndot.spring25.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： JDKProxyFactory.java
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
 * 创建时间: 2009-8-29
 * 
 */
public class JDKProxyFactory implements InvocationHandler {
	// 要代理的原始对象
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
			// 方法调用之前
			doBefore();
			// 调用原始对象的方法
			result = method.invoke(this.objOriginal, args);
			// 方法调用之后
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
