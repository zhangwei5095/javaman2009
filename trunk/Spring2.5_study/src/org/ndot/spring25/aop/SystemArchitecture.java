package org.ndot.spring25.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 *
 * 项目名称：Spring2.5_study
 * 
 *<P>
 *
 * 文件名： SystemArchitecture.java
 * 
 *<P>
 *
 * 功 能: 常用的AOP架构
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
public class SystemArchitecture {
	/**  
     * A join point is in the web layer if the method is defined in a type in  
     * the com.xyz.someapp.web package or any sub-package under that.  
     */  
    @Pointcut("within(com.xyz.someapp.web..*)")   
    public void inWebLayer() {   
    }   
  
    /**  
     * A join point is in the service layer if the method is defined in a type  
     * in the com.xyz.someapp.service package or any sub-package under that.  
     */  
    @Pointcut("within(com.xyz.someapp.service..*)")   
    public void inServiceLayer() {   
    }   
  
    /**  
     * A join point is in the data access layer if the method is defined in a  
     * type in the com.xyz.someapp.dao package or any sub-package under that.  
     */  
    @Pointcut("within(com.xyz.someapp.dao..*)")   
    public void inDataAccessLayer() {   
    }   
  
    /**  
     * A business service is the execution of any method defined on a service  
     * interface. This definition assumes that interfaces are placed in the  
     * "service" package, and that implementation types are in sub-packages.  
     *   
     * If you group service interfaces by functional area (for example, in  
     * packages com.xyz.someapp.abc.service and com.xyz.def.service) then the  
     * pointcut expression "execution(* com.xyz.someapp..service.*.*(..))" could  
     * be used instead.  
     */  
    @Pointcut("execution(* com.xyz.someapp.service.*.*(..))")   
    public void businessService() {   
    }   
  
    /**  
     * A data access operation is the execution of any method defined on a dao  
     * interface. This definition assumes that interfaces are placed in the  
     * "dao" package, and that implementation types are in sub-packages.  
     */  
    @Pointcut("execution(* com.xyz.someapp.dao.*.*(..))")   
    public void dataAccessOperation() {   
    }   


}
