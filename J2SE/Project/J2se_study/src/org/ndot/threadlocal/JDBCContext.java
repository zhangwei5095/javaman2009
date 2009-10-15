package org.ndot.threadlocal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： JDBCContext.java
 * 
 *<P>
 * 
 * 功 能:
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-10-14
 * 
 */
public class JDBCContext {
	private DataSource ds;
	protected Connection connection;
	private static ThreadLocal<JDBCContext> jdbcContext;

	private JDBCContext(DataSource ds) {
		this.ds = ds;
		createConnection();
	}

	private void createConnection() {
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static JDBCContext getJdbcContext(javax.sql.DataSource ds) {
		if (jdbcContext == null)
			jdbcContext = new ThreadLocal<JDBCContext>();
		JDBCContext context = (JDBCContext) jdbcContext.get();
		if (context == null) {
			context = new JDBCContext(ds);
		}
		return context;
	}


}
