package org.ndot.spring25.aop.xml;
/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 *
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 *
 * �ļ����� BServiceImpl.java
 * 
 *<P>
 *
 * �� ��:Service��B
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


public class BServiceImpl {

    public void barB(String _msg, int _type) {
        System.out.println("BServiceImpl.barB(msg:"+_msg+" type:"+_type+")");
        if(_type == 1)
            throw new IllegalArgumentException("�����쳣");
    }

    public void fooB() {
        System.out.println("BServiceImpl.fooB()");
    }

}
