package org.ndot.spring25.mutiDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� DynamicDataSource.java
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
 * ����ʱ��: 2010-3-3
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
