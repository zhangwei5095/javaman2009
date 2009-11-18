package org.ndot.adapter;

import flex.management.BaseControl;
import flex.management.runtime.messaging.services.ServiceAdapterControl;
import flex.management.runtime.messaging.services.ServiceAdapterControlMBean;
import flex.messaging.services.ServiceAdapter;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 *
 * 项目名称：Flex_J2ee
 * 
 *<P>
 *
 * 文件名： SimpleCustomAdapterControl.java
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
 * 创建时间: 2009-9-19
 * 
 */
public class SimpleCustomAdapterControl extends ServiceAdapterControl implements
SimpleCustomServiceAdapterControlMBean {
	private static final String TYPE = "SimpleCustomAdapter";
	public SimpleCustomAdapterControl(SimpleCustomAdapter serviceAdapter,
			BaseControl parent) {
		super(serviceAdapter, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	


}
