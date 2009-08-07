package org.ndot.jbpm4.db;


import org.hsqldb.util.DatabaseManagerSwing;

/**
 * project: TestHSqlDB
 * 
 * filename£ºStartTool.java
 * 
 * Function:
 * 
 * Copyright: Copyright (c) 2008
 * 
 * @author Ëï½ð³Ç
 * @version 1.0
 * @Date 2007-12-27
 */

public class StartTool {

	/**
	 * @param args
	 *            pram
	 */
	public static void main(String[] args) {
		Runtime run = Runtime.getRuntime();
		try {
			DatabaseManagerSwing dbm = new DatabaseManagerSwing();
			dbm.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
