package org.ndot.spring25.other.quartz;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
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
 * �ļ����� QuartzJobBean.java
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
 * ����ʱ��: 2009-9-1
 * 
 */
@Component
public class QuartzTaskServer extends QuartzJobBean {

	public QuartzTaskServer() {
		super();
		System.out.println("ÿ�ζ�����һ���¶��� ����ʼ");
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		System.out.println("ִ�ж�ʱ����..!"
				+ String.format("%1$tF %1$tT", new Date()));
	}
}
