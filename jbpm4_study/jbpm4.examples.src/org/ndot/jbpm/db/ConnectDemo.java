package org.ndot.jbpm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectDemo {

	/**
	 * @param args
	 *            pram
	 */
	public static void main(String[] args) {
		try {

			System.out.println("dd");
			Class.forName("org.hsqldb.jdbcDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:hsqldb:hsql://localhost:5998/jbpm4DB", "sa", "");
			Statement st = conn.createStatement();
			// st.execute("SHUTDOWN");
			conn.close();
		} catch (Exception e) {
			e = null;
		}
	}

}
