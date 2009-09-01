package org.ndot.spring25.other.quartz;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
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
 * 文件名： QuartzJobBean.java
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
 * 创建时间: 2009-9-1
 * 
 */
@Component
public class QuartzTaskServer extends QuartzJobBean {

	public QuartzTaskServer() {
		super();
		System.out.println("每次都创建一个新对象 任务开始");
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		System.out.println("执行定时任务..!"
				+ String.format("%1$tF %1$tT", new Date()));
	}
}
