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
 * �ļ����� AServiceImpl.java
 * 
 *<P>
 *
 * �� ��: �ӿ�A��ʵ����
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
