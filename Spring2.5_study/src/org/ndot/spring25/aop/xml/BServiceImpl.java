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
 * 文件名： BServiceImpl.java
 * 
 *<P>
 *
 * 功 能:Service类B
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


public class BServiceImpl {

    public void barB(String _msg, int _type) {
        System.out.println("BServiceImpl.barB(msg:"+_msg+" type:"+_type+")");
        if(_type == 1)
            throw new IllegalArgumentException("测试异常");
    }

    public void fooB() {
        System.out.println("BServiceImpl.fooB()");
    }

}
