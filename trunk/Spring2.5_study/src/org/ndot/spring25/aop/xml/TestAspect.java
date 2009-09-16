package org.ndot.spring25.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 *
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 *
 * �ļ����� TestAspect.java
 * 
 *<P>
 *
 * �� ��: ����һ������
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


public class TestAspect {

    public void doAfter(JoinPoint jp) {
        System.out.println("log Ending method: "
                + jp.getTarget().getClass().getName() + "."
                + jp.getSignature().getName());
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        System.out.println("process time: " + time + " ms");
        return retVal;
    }

    public void doBefore(JoinPoint jp) {
        System.out.println("log Begining method: "
                + jp.getTarget().getClass().getName() + "."
                + jp.getSignature().getName());
    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        System.out.println("method " + jp.getTarget().getClass().getName()
                + "." + jp.getSignature().getName() + " throw exception");
        System.out.println(ex.getMessage());
    }

    private void sendEx(String ex) {
        //TODO ���Ͷ��Ż��ʼ�����
    }
} 
