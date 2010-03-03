package org.ndot.spring25.mutiDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： DynamicDataSource.java
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
 * 创建时间: 2010-3-3
 * 
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	public static ThreadLocal<String> currentLookupKey = new ThreadLocal<String>();

	@Override
	protected Object determineCurrentLookupKey() {
		String ckey = DynamicDataSource.currentLookupKey.get();
		System.out.println(ckey);
		return ckey;
	}

}
