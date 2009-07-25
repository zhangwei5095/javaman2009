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
 * filename��HsqlListener.java
 * 
 * Function: �����ְ������WebApp����ʱ�Զ�����HSQL����. ��Ȼʹ��Server��ʽ������AppServer��Ӱ��.
 * 
 * Copyright: Copyright (c) 2008
 * 
 * @author ����
 * @version 1.0
 * @Date 2007-12-27
 */

public class HSqlDBListener implements ServletContextListener {

	/**
	 * Listener���ٷ�������WebӦ����ֹ��ʱ��ִ��"shutdown"����ر����ݿ�.
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
		// ���´����������ݿ�ָ��ġ����ǰ�ԭʼ�����ݿ����classpath�£�������web��ʱ�򣬼��Ŀ��
		// ���ݿ��Ƿ���ڣ���������ڣ��Ͱ�ԭʼ���ݿ⸴��Ϊָ�������ݿ�
		if (!path.endsWith("/")) {
			path = path + "/";
		}
		File scriptFile = new File(path + dbName + ".script");
		File propertiesFile = new File(path + dbName + ".properties");
		if (scriptFile.exists() && propertiesFile.exists()) {
			this.startServer(path, dbName, port);
		} else {
			System.out.println("�������ӵ�hsqldb...");
		}

	}

	/**
	 * * ����Hsqldb����ķ����� *
	 * 
	 * @param dbPath
	 *            ���ݿ�·�� *
	 * @param dbName
	 *            ���ݿ����� *
	 * @param port
	 *            ��ʹ�õĶ˿ں�
	 */
	private void startServer(String dbPath, String dbName, int port) {
		Server server = new Server();// hsqldb.jar�е���
		server.setDatabaseName(0, dbName);
		server.setDatabasePath(0, dbPath + dbName);

		if (port != -1) {
			server.setPort(port);
		}
		server.setSilent(true);
		server.start();
		System.out.println("hsqldb started...");
		// �ȴ�Server����
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
