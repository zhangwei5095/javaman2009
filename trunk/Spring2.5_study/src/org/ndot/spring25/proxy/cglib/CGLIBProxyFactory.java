package org.ndot.spring25.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� JCGLIBProxyFactory.java
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
public class CGLIBProxyFactory implements MethodInterceptor {
	private Object tartgetObject;

	public Object createProxyIntance(Object targetObject) {
		this.tartgetObject = targetObject;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.tartgetObject.getClass());// ��final�ķ������и���
		enhancer.setCallback(this);
		return enhancer.create();
	}

	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		Object result = null;
		if (method.getName().endsWith("sayHello")) {
			// ��������֮ǰ
			doBefore();
			// ����ԭʼ����ķ���
			result = method.invoke(tartgetObject, args);
			// ��������֮��
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
