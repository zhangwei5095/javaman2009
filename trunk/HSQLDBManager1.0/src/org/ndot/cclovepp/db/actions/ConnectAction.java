package org.ndot.cclovepp.db.actions;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.hsqldb.Server;
import org.ndot.cclovepp.db.NDotDB;
import org.ndot.cclovepp.db.config.DatabaseConfig;
import org.ndot.cclovepp.db.tree.TreeElement;
import org.ndot.cclovepp.db.util.WidgetTool;

public class ConnectAction extends Action {

	private NDotDB ndotdb;

	public ConnectAction(NDotDB db) {
		super("����DB(&C)");
		this.ndotdb = db;
	}

	public void run() {

		StructuredSelection select = (StructuredSelection) ndotdb
				.getDbConfigTreeViewer().getSelection();
		TreeElement element = (TreeElement) select.getFirstElement();
		String id = element.getId();
		element = null;
		if (id != null) {
			DatabaseConfig dbc = (DatabaseConfig)select.getFirstElement();
			Server curServer = ndotdb.getCurServie();
			if (curServer != null) {
				int state = curServer.getState();
				// "1:ONLINE 4:OPENING 8:CLOSING, 16:SHUTDOWN"
				if (state == 1 || state == 4) {
					curServer.shutdown();
				}				
			}
			curServer = ndotdb.getConfigServies().get(id);
			curServer.start();
			System.out.println("����idΪ ��" + id + " �����ݿ��Ѿ�����...");
			setConnPropertys(dbc);
		} else {
			MessageDialog.openConfirm(ndotdb.getShell(), "������ʾ",
					"��ѡ����ȷ�����ݿ����ýڵ�:)");
		}
		System.out.println("����IDΪ" + id + "���ݿ�");
	}

	private void setConnPropertys(DatabaseConfig dbc) {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
//			String url ="jdbc:hsqldb:hsql://localhost:"+dbc.getPort()+"/"+dbc.getDbname();
			String url = "jdbc:hsqldb:hsql://localhost:9001/ndotdb";
			Connection conn = DriverManager.getConnection(
					url, "sa", "");
			DatabaseMetaData dMeta = conn.getMetaData();
			Statement st = conn.createStatement();
			ndotdb.setConn(conn);
            ndotdb.setDMeta(dMeta);
            ndotdb.setSt(st);
            ndotdb.getDbconnectTreeViewer().setInput(WidgetTool.getConnectTree(dMeta));
            ndotdb.getCTabFolder().setSelection(ndotdb.getCurdbItem());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
