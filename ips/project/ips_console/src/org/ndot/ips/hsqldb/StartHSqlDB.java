package org.ndot.ips.hsqldb;

import org.hsqldb.Server;

public class StartHSqlDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime run = Runtime.getRuntime(); 
		String dburl = "D:/NDotIPS/hsqldb/ipsdb/ipsdb";
//		String dburl=""c:\\MyTools\\NDot\\hsqldb\\ipsdb\\ipsdb"";
		try {	
			Server curServer = new Server();
			curServer.setAddress("localhost");
			curServer.setDatabaseName(0,"ipsdb");
			curServer.setDatabasePath(0,dburl);
			curServer.setPort(9099);
			int state = curServer.getState();
			curServer.start();
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

}
