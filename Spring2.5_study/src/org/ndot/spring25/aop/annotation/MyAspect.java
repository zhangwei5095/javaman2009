package org.ndot.spring25.aop.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 *
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 *
 * �ļ����� MyAspect.java
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
@Aspect
@Component
public class MyAspect {
	// execution���,����ͨ�� & || !�����������ʽ������   
    // �����Ǳ��ʽ������ͨ��������ʶ���ñ��ʽ   
    @Pointcut("execution(public void org.ndot.spring25.aop.annotation.HelloWorld.sayHello*(String))")   
    public void helloworld() {   
    }   
  
    @Before("execution(public void org.ndot.spring25.aop.annotation.HelloWorld.sayHello*(..))")   
    public void beforeSayHello() {   
        System.out.println("before sayHello*");   
    }   
  
    @After("helloworld()")   
    public void afterSayHello() {   
        System.out.println("after sayHello*");   
    }   
  
    @AfterThrowing("org.ndot.spring25.aop.annotation.MyAspect.helloworld()")   
    public void exceptionSayHello() {   
        System.out.println("throw runtime exception");   
    }   
  
    @AfterReturning("org.ndot.spring25.aop.annotation.MyAspect.helloworld()")   
    public void returnSayHello() {   
        System.out.println("method has returned");   
    }   
  
    @Around("org.ndot.spring25.aop.annotation.MyAspect.helloworld()")   
    public Object aroundSayHello(ProceedingJoinPoint pjp) {   
        Object obj = null;   
        try {   
            System.out.println("around start");   
            obj = pjp.proceed();   
            System.out.println("around end");   
        } catch (Throwable e) {   
            e.printStackTrace();   
        }   
        return obj;   
    }   
       
    /*  
 
���⹫��������ִ�У�  
 
execution(public * *(..))  
�κ�һ���ԡ�set����ʼ�ķ�����ִ�У�  
 
execution(* set*(..))  
AccountService �ӿڵ����ⷽ����ִ�У�  
 
execution(* com.xyz.service.AccountService.*(..))  
������service��������ⷽ����ִ�У�  
 
execution(* com.xyz.service.*.*(..))  
������service�������Ӱ�������ⷽ����ִ�У�  
 
execution(* com.xyz.service..*.*(..))  
��service������������ӵ㣨��Spring AOP��ֻ�Ƿ���ִ�У� ��  
 
within(com.xyz.service.*)  
��service�������Ӱ�����������ӵ㣨��Spring AOP��ֻ�Ƿ���ִ�У� ��  
 
within(com.xyz.service..*)  
ʵ���� AccountService �ӿڵĴ��������������ӵ㣨��Spring AOP��ֻ�Ƿ���ִ�У� ��  
 
this(com.xyz.service.AccountService)  
'this'��binding form���õĸ��ࣺ- �볣����������֪ͨ���½��й������ʹ�ô�����������֪ͨ���ڷ��ʵ��Ĳ��֡�  
ʵ���� AccountService �ӿڵ�Ŀ�������������ӵ㣨��Spring AOP��ֻ�Ƿ���ִ�У� ��  
 
target(com.xyz.service.AccountService)  
'target'��binding form���õĸ��ࣺ- �볣����������֪ͨ���½��й������ʹ��Ŀ����������֪ͨ���ڷ��ʵ��Ĳ��֡�  
�κ�һ��ֻ����һ����������������ʱ����Ĳ���ʵ���� Serializable �ӿڵ����ӵ� ����Spring AOP��ֻ�Ƿ���ִ�У�   
 
args(java.io.Serializable)  
'args'��binding form���õĸ��ࣺ- �볣����������֪ͨ���½��й������ʹ�÷�������������֪ͨ���ڷ��ʵ��Ĳ��֡�  
��ע���������и���������㲻ͬ�� execution(* *(java.io.Serializable))�� argsֻ���ڶ�̬����ʱ��������ǿ����л��ģ�Serializable����ƥ�䣬��execution �ڴ��������ǩ������������ʵ���� Serializable �ӿ�ʱ��ƥ�䡣  
 
��һ�� @Transactional ע���Ŀ������е��������ӵ㣨��Spring AOP��ֻ�Ƿ���ִ�У�   
 
@target(org.springframework.transaction.annotation.Transactional)  
'@target' Ҳ������binding form��ʹ�ã��볣����������֪ͨ���½��й������ʹ��annotation���������֪ͨ���ڷ��ʵ��Ĳ��֡�  
�κ�һ��Ŀ�����������������һ�� @Transactional ע������ӵ㣨��Spring AOP��ֻ�Ƿ���ִ�У�  
 
@within(org.springframework.transaction.annotation.Transactional)  
'@within'Ҳ������binding form��ʹ�ã�- �볣����������֪ͨ���½��й������ʹ��annotation���������֪ͨ���ڷ��ʵ��Ĳ��֡�  
�κ�һ��ִ�еķ�����һ�� @Transactional annotation�����ӵ㣨��Spring AOP��ֻ�Ƿ���ִ�У�   
 
@annotation(org.springframework.transaction.annotation.Transactional)  
'@annotation' Ҳ������binding form��ʹ�ã�- �볣����������֪ͨ���½��й������ʹ��annotation���������֪ͨ���ڷ��ʵ��Ĳ��֡�  
�κ�һ������һ�����������Ҵ���Ĳ���������ʱ������ʵ���� @Classified annotation�����ӵ㣨��Spring AOP��ֻ�Ƿ���ִ�У�   
 
@args(com.xyz.security.Classified)  
'@args'Ҳ������binding form��ʹ�ã�- �볣����������֪ͨ���½��й������ʹ��annotation���������֪ͨ���ڷ��ʵ��Ĳ��֡�  
 
*/  

}
