package org.ndot.jbpm4.db;

import org.hsqldb.Server;

public class HQLDBStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Runtime run = Runtime.getRuntime(); 
		try {	
			Server curServer = new Server();
			curServer.setAddress("localhost");
			curServer.setDatabaseName(0,"jbpm4DB");
			curServer.setDatabasePath(0, "C:/MyTools/jbpm-4.0/db/hqldb/jbpm4DB");
			curServer.setPort(5998);
			int state = curServer.getState();
			curServer.start();
		} catch (Exception e) {
            e.printStackTrace();
		}

	}

}
