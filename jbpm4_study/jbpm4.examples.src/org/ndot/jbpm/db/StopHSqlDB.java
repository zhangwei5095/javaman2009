package org.ndot.jbpm.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class StopHSqlDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:hsqldb:hsql://localhost:9099/ipsdb", "sa", "");
			Statement st = conn.createStatement();
			st = conn.createStatement();
			st.execute("SHUTDOWN");
			conn.close();
		} catch (Exception e) {
			e = null;
		}
	}

}
