package org.ndot.spring25.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： JCGLIBProxyFactory.java
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
public class CGLIBProxyFactory implements MethodInterceptor {
	private Object tartgetObject;

	public Object createProxyIntance(Object targetObject) {
		this.tartgetObject = targetObject;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.tartgetObject.getClass());// 非final的方法进行覆盖
		enhancer.setCallback(this);
		return enhancer.create();
	}

	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		Object result = null;
		if (method.getName().endsWith("sayHello")) {
			// 方法调用之前
			doBefore();
			// 调用原始对象的方法
			result = method.invoke(tartgetObject, args);
			// 方法调用之后
			doAfter();

		} else {
			result = method.invoke(tartgetObject, args);
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
		CGLIBProxyFactory pf = new CGLIBProxyFactory();
		helloImp = (HelloImp) pf.createProxyIntance(helloImp);
		helloImp.sayHello("NDot");

	}
}
