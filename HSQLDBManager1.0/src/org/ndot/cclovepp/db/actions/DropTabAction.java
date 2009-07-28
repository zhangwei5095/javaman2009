package org.ndot.cclovepp.db.actions;

import java.sql.SQLException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.ndot.cclovepp.db.NDotDB;
import org.ndot.cclovepp.db.tree.TreeElement;

public class DropTabAction extends Action {
	private NDotDB ndotdb;

	public DropTabAction(NDotDB db) {
		super("删除该表结构");
		this.ndotdb = db;
	}

	public void run() {
		StructuredSelection select = (StructuredSelection) ndotdb
				.getDbconnectTreeViewer().getSelection();
		TreeElement element = (TreeElement) select.getFirstElement();
		String name = element.getName();

		if (name.contains("PUBLIC.")) {
			boolean isDorp = MessageDialog.openQuestion(ndotdb.getShell(),
					"友情提示", "您确认删除该表格吗？");
			if (isDorp) {
				String sql = "drop table " + name;
				ndotdb.getDocument().set(sql);
				ndotdb.getDocument().set(sql);
			}
		} else {
			MessageDialog.openConfirm(ndotdb.getShell(), "友情提示",
					"请选择正确的数据表配置节点:)");
		}

	}
}
