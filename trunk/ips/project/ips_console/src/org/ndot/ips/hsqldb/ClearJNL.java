package org.ndot.ips.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ClearJNL {
	public void init() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:hsqldb:hsql://localhost:9099/ipsdb", "sa", "");
			Statement st = conn.createStatement();
			st.execute("delete from  IPS_JNL_TODAY ");
			st.close();
			conn.close();
		} catch (Exception e) {
			e = null;
		}
	}
}
