package org.ndot.ips.junit;

import org.junit.Test;
import org.ndot.ips.hsqldb.StartHSqlDB;
import org.ndot.ips.hsqldb.StopHSqlDB;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： TestMain.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-24
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
