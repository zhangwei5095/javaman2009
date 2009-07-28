package org.ndot.cclovepp.db.util;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.hsqldb.Server;
import org.ndot.cclovepp.db.NDotDB;
import org.ndot.cclovepp.db.actions.AboutAction;
import org.ndot.cclovepp.db.actions.AutoCommitAction;
import org.ndot.cclovepp.db.actions.ClearAction;
import org.ndot.cclovepp.db.actions.CommitAction;
import org.ndot.cclovepp.db.actions.ConnectAction;
import org.ndot.cclovepp.db.actions.DelectDBAction;
import org.ndot.cclovepp.db.actions.DropTabAction;
import org.ndot.cclovepp.db.actions.ExecuteAction;
import org.ndot.cclovepp.db.actions.ExitAction;
import org.ndot.cclovepp.db.actions.HelpContentAction;
import org.ndot.cclovepp.db.actions.NewDBAction;
import org.ndot.cclovepp.db.actions.OpenAction;
import org.ndot.cclovepp.db.actions.RefreshTreeAction;
import org.ndot.cclovepp.db.actions.RollBackAction;
import org.ndot.cclovepp.db.actions.SaveAction;
import org.ndot.cclovepp.db.actions.SetFontAction;
import org.ndot.cclovepp.db.actions.ShowScheams;
import org.ndot.cclovepp.db.actions.ShowSysTabAction;
import org.ndot.cclovepp.db.actions.ShowTabDataAction;
import org.ndot.cclovepp.db.actions.SqlSelectAction;
import org.ndot.cclovepp.db.config.DBConfigBuilder;
import org.ndot.cclovepp.db.config.DatabaseConfig;
import org.ndot.cclovepp.db.tree.DbConfigTreeElement;
import org.ndot.cclovepp.db.tree.TreeElement;

public class WidgetTool {
	public static MenuManager createMenu(NDotDB db) {
		MenuManager top = new MenuManager();
		MenuManager fileMenu = new MenuManager("文件(&F)");
		MenuManager viewMenu = new MenuManager("视图(&V)");
		MenuManager commMenu = new MenuManager("命令(&C)");
		MenuManager optionMenu = new MenuManager("选项(&O)");
		MenuManager helpMenu = new MenuManager("帮助(&H)");

		fileMenu.add(new OpenAction(db));
		fileMenu.add(new SaveAction(db));
		fileMenu.add(new Separator());

		fileMenu.add(new NewDBAction(db));
		fileMenu.add(new ConnectAction(db));
		fileMenu.add(new Separator());
		fileMenu.add(new ExitAction(db));

		viewMenu.add(new RefreshTreeAction(db));
		viewMenu.add(new Separator());
		viewMenu.add(new ShowSysTabAction(db));
		viewMenu.add(new ShowScheams(db));

		commMenu.add(new SqlSelectAction(db));

		optionMenu.add(new SetFontAction(db));
		optionMenu.add(new Separator());
		optionMenu.add(new CommitAction(db));
		optionMenu.add(new RollBackAction(db));
		optionMenu.add(new Separator());
		optionMenu.add(new AutoCommitAction(db));

		helpMenu.add(new HelpContentAction(db));
		helpMenu.add(new AboutAction(db));

		top.add(fileMenu);
		top.add(viewMenu);
		top.add(commMenu);
		top.add(optionMenu);
		top.add(helpMenu);

		return top;
	}

	public static CoolBarManager createCoolBar(NDotDB db, int style) {
		CoolBarManager coolBarManager = new CoolBarManager(style);
		coolBarManager.add(createToolBar(db, style));
		return coolBarManager;
	}

	public static ToolBarManager createToolBar(NDotDB db, int style) {
		ToolBarManager toolBar = new ToolBarManager(style);
		toolBar.add(new NewDBAction(db));
		toolBar.add(new ConnectAction(db));
		toolBar.add(new Separator());

		toolBar.add(new RollBackAction(db));
		toolBar.add(new CommitAction(db));
		toolBar.add(new Separator());

		toolBar.add(new ClearAction(db));
		toolBar.add(new ExecuteAction(db));
		toolBar.add(new Separator());

		return toolBar;
	}

	public static List<TreeElement> getDBConfig() {
		List<TreeElement> init = new ArrayList<TreeElement>();
		DbConfigTreeElement dbcs = new DbConfigTreeElement("已有数据库");
		try {
			dbcs.setChildrens(DBConfigBuilder.getInstance().getDBList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		init.add(dbcs);
		init.add(new DbConfigTreeElement("其他"));
		return init;
	}

	public static List<String[]> getTableElement(ResultSet rst)
			throws Exception {

		List<String[]> tElems = new ArrayList<String[]>();
		if (rst == null) {
			tElems.add(new String[] { "暂无记录" });
			return tElems;
		}

		ResultSetMetaData m = rst.getMetaData();
		int col = m.getColumnCount();
		boolean[] isVarChar = new boolean[col];
		for (int i = 1; i <= col; i++) {
			isVarChar[i - 1] = (m.getColumnType(i) == java.sql.Types.VARCHAR);
		}

		while (rst.next()) {
			String[] tem = new String[col];
			for (int i = 1; i <= col; i++) {
				tem[i - 1] = rst.getString(i);
				if (rst.wasNull()) {
					tem[i - 1] = (isVarChar[i - 1] ? "[null]" : null);
				}
			}
			tElems.add(tem);
		}
		rst.close();
		return tElems;

	}

	public static String[] getTableHead(ResultSet rst) throws Exception {
		if (rst == null) {
			return new String[] { "返回数据" };
		}
		ResultSetMetaData m = rst.getMetaData();
		int col = m.getColumnCount();
		String[] h = new String[col];
		for (int i = 1; i <= col; i++) {
			h[i - 1] = m.getColumnLabel(i);
		}
		return h;
	}

	public static Menu createDBContextMenu(NDotDB db, Composite parent) {
		MenuManager top = new MenuManager();
		top.add(new NewDBAction(db));
		top.add(new Separator());
		top.add(new ConnectAction(db));
		top.add(new Separator());
		top.add(new DelectDBAction(db));
		return top.createContextMenu(parent);
	}
	
	public static Menu createTableMenu(NDotDB db, Composite parent) {
		MenuManager top = new MenuManager();
		top.add(new ShowTabDataAction(db));
		top.add(new Separator());	
		top.add(new DropTabAction(db));
		return top.createContextMenu(parent);
	}

	// 获得所配置的所有server
	public static HashMap<String, Server> getConfigServers() {
		HashMap servers = new HashMap<String, Server>();
		List<TreeElement> dbcList;
		try {
			dbcList = DBConfigBuilder.getInstance().getDBList();
			int i = 0;
			for (Iterator iter = dbcList.iterator(); iter.hasNext();) {

				DatabaseConfig dbc = (DatabaseConfig) iter.next();
				Server s = new Server();
				s.setDatabaseName(i, dbc.getDbname());
				s.setDatabasePath(i, dbc.getDbpath());
				s.setPort(dbc.getPort());
				s.setSilent(dbc.isSilent());

				servers.put(dbc.getId(), s);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return servers;
	}

	public static List<TreeElement> getConnectTree(DatabaseMetaData dMeta) {
		List<TreeElement> init = new ArrayList<TreeElement>();
		try {
			// 当前数据库连接的信息，根节点
			DbConfigTreeElement root = new DbConfigTreeElement(dMeta.getURL());

			String[] usertables = { "TABLE", "GLOBAL TEMPORARY", "VIEW" };
			ResultSet result = dMeta.getTables(null, null, null, usertables);

			Vector schemas = new Vector();
			Vector tables = new Vector();

			Vector remarks = new Vector();

			while (result.next()) {
				schemas.addElement(result.getString(2));
				tables.addElement(result.getString(3));
				remarks.addElement(result.getString(5));
			}

			result.close();

			for (int i = 0; i < tables.size(); i++) {
				String name = (String) tables.elementAt(i);
				String schema = (String) schemas.elementAt(i);
				DbConfigTreeElement tab = new DbConfigTreeElement(schema + "."
						+ name);

//				String remark = (String) remarks.elementAt(i);

//				if ((schema != null) && !schema.trim().equals("")) {
//					// tTree.addRow(key + "s", "schema: " + schema);
//				}
//
//				if ((remark != null) && !remark.trim().equals("")) {
//					// tTree.addRow(key + "r", " " + remark);
//				}

				ResultSet col = dMeta.getColumns(null, schema, name, null);

				while (col.next()) {
					String c = col.getString(4);
					DbConfigTreeElement field = new DbConfigTreeElement(c);

					String type = col.getString(6);
					DbConfigTreeElement fieldtype = new DbConfigTreeElement(
							"Type - "+type);

					boolean nullable = col.getInt(11) != DatabaseMetaData.columnNoNulls;
					DbConfigTreeElement isnull = new DbConfigTreeElement("Nullable - "+String
							.valueOf(nullable));
					field.addChildren(fieldtype);
					field.addChildren(isnull);

					tab.addChildren(field);
				}

				// 表的索引情况
				DbConfigTreeElement index = new DbConfigTreeElement("INDICES");

				ResultSet ind = dMeta.getIndexInfo(null, schema, name, false,
						false);
				String oldiname = null;

				while (ind.next()) {
					boolean nonunique = ind.getBoolean(4);
					String iname = ind.getString(6);

					if ((oldiname == null || !oldiname.equals(iname))) {
						DbConfigTreeElement indexname = new DbConfigTreeElement(
								"IndexName - "+iname);
						DbConfigTreeElement indexu = new DbConfigTreeElement("NonUnique - "+
								String.valueOf(!nonunique));
						index.addChildren(indexname);
						index.addChildren(indexu);
						oldiname = iname;
					}

					String c = ind.getString(9);
					DbConfigTreeElement indexc = new DbConfigTreeElement("IndexField - "+c);
					index.addChildren(indexc);
				}
				tab.addChildren(index);
				ind.close();
				root.addChildren(tab);
			}
			init.add(root);
			
			// tTree.addRow("p", "Properties", "+", 0);
			// tTree.addRow("pu", "User: " + dMeta.getUserName());
			// tTree.addRow("pr", "ReadOnly: " + cConn.isReadOnly());
			// tTree.addRow("pa", "AutoCommit: " + cConn.getAutoCommit());
			// tTree.addRow("pd", "Driver: " + dMeta.getDriverName());
			// tTree.addRow("pp", "Product: " + dMeta.getDatabaseProductName());
			// tTree.addRow("pv", "Version: " +
			// dMeta.getDatabaseProductVersion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(init.size()<1) init.add(new DbConfigTreeElement("没有创建用户表"));
		return init;
	}
}
