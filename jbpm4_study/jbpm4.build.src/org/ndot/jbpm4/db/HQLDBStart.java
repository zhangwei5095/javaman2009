package org.ndot.jbpm4.db;

import org.hsqldb.Server;

public class HQLDBStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime run = Runtime.getRuntime();
		String dburl = "C:/MyTools/E7Workplace/jbpm4_study/jbpm4_study/WEB-INF/hsqldb/jbpm4DB";
		try {
			Server curServer = new Server();
			curServer.setAddress("localhost");
			curServer.setDatabaseName(0, "jbpm4DB");
			curServer.setDatabasePath(0, dburl);
			curServer.setPort(5998);
			int state = curServer.getState();
			curServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
