package org.ndot.spring25.aop.xml;
/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 *
 * 项目名称：Spring2.5_study
 * 
 *<P>
 *
 * 文件名： AServiceImpl.java
 * 
 *<P>
 *
 * 功 能: 接口A的实现类
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


public class AServiceImpl implements AService  {

    /* (non-Javadoc)
	 * @see org.ndot.spring25.aop.xml.AService#barA()
	 */
    public void barA() {
        System.out.println("AServiceImpl.barA()");
    }

    /* (non-Javadoc)
	 * @see org.ndot.spring25.aop.xml.AService#fooA(java.lang.String)
	 */
    public void fooA(String _msg) {
        System.out.println("AServiceImpl.fooA(msg:"+_msg+")");
    }
}
