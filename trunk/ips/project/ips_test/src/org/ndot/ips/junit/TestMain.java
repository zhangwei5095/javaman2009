package org.ndot.ips.junit;

import org.junit.Test;
import org.ndot.ips.hsqldb.StartHSqlDB;
import org.ndot.ips.hsqldb.StopHSqlDB;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� TestMain.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-24
 * 
 */
public class TestMain {
	@Test
	public void startHSQLDB(){
		StartHSqlDB.main(null);
	}
	@Test
	public void stopHSQLDB(){
		StopHSqlDB.main(null);
	}
	
}
