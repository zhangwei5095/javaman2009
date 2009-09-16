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
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 *
 * 项目名称：Spring2.5_study
 * 
 *<P>
 *
 * 文件名： MyAspect.java
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
@Aspect
@Component
public class MyAspect {
	// execution最常用,可以通过 & || !进行切入点表达式的连接   
    // 可是是表达式，可以通过切入点标识重用表达式   
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
 
任意公共方法的执行：  
 
execution(public * *(..))  
任何一个以“set”开始的方法的执行：  
 
execution(* set*(..))  
AccountService 接口的任意方法的执行：  
 
execution(* com.xyz.service.AccountService.*(..))  
定义在service包里的任意方法的执行：  
 
execution(* com.xyz.service.*.*(..))  
定义在service包或者子包里的任意方法的执行：  
 
execution(* com.xyz.service..*.*(..))  
在service包里的任意连接点（在Spring AOP中只是方法执行） ：  
 
within(com.xyz.service.*)  
在service包或者子包里的任意连接点（在Spring AOP中只是方法执行） ：  
 
within(com.xyz.service..*)  
实现了 AccountService 接口的代理对象的任意连接点（在Spring AOP中只是方法执行） ：  
 
this(com.xyz.service.AccountService)  
'this'在binding form中用的更多：- 请常见以下讨论通知的章节中关于如何使得代理对象可以在通知体内访问到的部分。  
实现了 AccountService 接口的目标对象的任意连接点（在Spring AOP中只是方法执行） ：  
 
target(com.xyz.service.AccountService)  
'target'在binding form中用的更多：- 请常见以下讨论通知的章节中关于如何使得目标对象可以在通知体内访问到的部分。  
任何一个只接受一个参数，且在运行时传入的参数实现了 Serializable 接口的连接点 （在Spring AOP中只是方法执行）   
 
args(java.io.Serializable)  
'args'在binding form中用的更多：- 请常见以下讨论通知的章节中关于如何使得方法参数可以在通知体内访问到的部分。  
请注意在例子中给出的切入点不同于 execution(* *(java.io.Serializable))： args只有在动态运行时候传入参数是可序列化的（Serializable）才匹配，而execution 在传入参数的签名声明的类型实现了 Serializable 接口时候匹配。  
 
有一个 @Transactional 注解的目标对象中的任意连接点（在Spring AOP中只是方法执行）   
 
@target(org.springframework.transaction.annotation.Transactional)  
'@target' 也可以在binding form中使用：请常见以下讨论通知的章节中关于如何使得annotation对象可以在通知体内访问到的部分。  
任何一个目标对象声明的类型有一个 @Transactional 注解的连接点（在Spring AOP中只是方法执行）  
 
@within(org.springframework.transaction.annotation.Transactional)  
'@within'也可以在binding form中使用：- 请常见以下讨论通知的章节中关于如何使得annotation对象可以在通知体内访问到的部分。  
任何一个执行的方法有一个 @Transactional annotation的连接点（在Spring AOP中只是方法执行）   
 
@annotation(org.springframework.transaction.annotation.Transactional)  
'@annotation' 也可以在binding form中使用：- 请常见以下讨论通知的章节中关于如何使得annotation对象可以在通知体内访问到的部分。  
任何一个接受一个参数，并且传入的参数在运行时的类型实现了 @Classified annotation的连接点（在Spring AOP中只是方法执行）   
 
@args(com.xyz.security.Classified)  
'@args'也可以在binding form中使用：- 请常见以下讨论通知的章节中关于如何使得annotation对象可以在通知体内访问到的部分。  
 
*/  

}
