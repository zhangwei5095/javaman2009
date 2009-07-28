package org.ndot.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProcessResult {
	private Connection conn;

	private Statement st;

	public ProcessResult() {
		init();
	}

	private void init() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection(
					"jdbc:hsqldb:hsql://localhost:9001/ndotdb", "sa", "");
			st = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet exceSql(String sql) throws Exception{
		return st.executeQuery(sql);
	}
	
	public List<String[]> getTableElement(ResultSet rst) throws Exception {
		List<String[]> tElems = new ArrayList<String[]>();
		ResultSetMetaData m = rst.getMetaData();
		int col = m.getColumnCount();		
		while (rst.next()) {	
			String[] tem = new String[col];
			for (int i = 1; i <= col; i++) {
				tem[i - 1] = rst.getString(i);
				if (rst.wasNull()) {
					tem[i - 1] = "(null)";
				}
			}
			tElems.add(tem);
		}
		return tElems;
	}

	public String[] getTableHead(ResultSet rst) throws Exception {
			ResultSetMetaData m = rst.getMetaData();
		int col = m.getColumnCount();
		String[] h = new String[col];
		for (int i = 1; i <= col; i++) {
			h[i - 1] = m.getColumnLabel(i);
		}
		return h;
	}
	
	public static void main(String[] args) {
		try {
			ProcessResult p = new ProcessResult();			
			ResultSet rst = p.exceSql("select * from students");			
			String[] head = p.getTableHead(rst);
			System.out.println(">---------------------------------------------------<");
			for (int i = 0; i < head.length; i++) {
				System.out.print("|"+head[i]);				
			}
			System.out.print("|");
			System.out.println();
			List<String[]> te = p.getTableElement(rst);
			
			for(String[] str:te){
				for (int i = 0; i < str.length; i++) {
					System.out.print("|"+str[i]);				
				}
				System.out.print("|");
				System.out.println();
			}
			System.out.println(">---------------------------------------------------<");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
