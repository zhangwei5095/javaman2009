package org.ndot.ips.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hsqldb.Server;

/**
 * project: TestHSqlDB
 * 
 * filename：HsqlListener.java
 * 
 * Function: 该类的职责是在WebApp启动时自动开启HSQL服务. 依然使用Server方式，不受AppServer的影响.
 * 
 * Copyright: Copyright (c) 2008
 * 
 * @author 孙金城
 * @version 1.0
 * @Date 2007-12-27
 */

public class HSqlDBListener implements ServletContextListener {

	/**
	 * Listener销毁方法，在Web应用终止的时候执行"shutdown"命令关闭数据库.
	 */
	public void contextInitialized(ServletContextEvent sce) {

		String path = sce.getServletContext().getInitParameter("hsql.dbPath");
		System.out.println("hsql.dbPath=" + path);
		String dbName = sce.getServletContext().getInitParameter("hsql.dbName");
		System.out.println("hsql.dbName=" + dbName);
		int port = -1;
		try {
			port = Integer.parseInt(sce.getServletContext().getInitParameter(
					"hsql.port"));
		} catch (Exception e) {
			port = 9001;
			e.printStackTrace();
		}
		if (dbName == null || dbName.equals("")) {
			System.out
					.println("Cant' get hsqldb.dbName from web.xml Context Param");
			return;
		}
		File dbDir = new File(path);
		if (!dbDir.exists()) {
			if (!dbDir.mkdirs()) {
				System.out.println("Can not create DB Dir for Hsql:" + dbDir);
				return;
			}
		}
		// 以下代码是做数据库恢复的。我们把原始的数据库放在classpath下，当启动web的时候，检查目标
		// 数据库是否存在，如果不存在，就把原始数据库复制为指定的数据库
		if (!path.endsWith("/")) {
			path = path + "/";
		}
		File scriptFile = new File(path + dbName + ".script");
		File propertiesFile = new File(path + dbName + ".properties");
		if (scriptFile.exists() && propertiesFile.exists()) {
			this.startServer(path, dbName, port);
		} else {
			System.out.println("不能连接到hsqldb...");
		}

	}

	/**
	 * * 启动Hsqldb服务的方法。 *
	 * 
	 * @param dbPath
	 *            数据库路径 *
	 * @param dbName
	 *            数据库名称 *
	 * @param port
	 *            所使用的端口号
	 */
	private void startServer(String dbPath, String dbName, int port) {
		Server server = new Server();// hsqldb.jar中的类
		server.setDatabaseName(0, dbName);
		server.setDatabasePath(0, dbPath + dbName);

		if (port != -1) {
			server.setPort(port);
		}
		server.setSilent(true);
		server.start();
		System.out.println("hsqldb started...");
		// 等待Server启动
		try {
			Thread.sleep(800);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		String dbName = sce.getServletContext().getInitParameter("hsql.dbName");
		int port = 9001;
		try {
			port = Integer.parseInt(sce.getServletContext().getInitParameter(
					"hsql.port"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:hsqldb:hsql://localhost:" + port + "/" + dbName,
					"sa", "");
			Statement st = conn.createStatement();
			st = conn.createStatement();
			st.execute("SHUTDOWN");
			conn.close();
		} catch (Exception e) {
			e = null;
		}

	}
}
