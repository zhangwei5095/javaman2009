package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class SqlSelectAction extends Action {
	private NDotDB ndotdb;

	public SqlSelectAction(NDotDB db) {
		super("Select-SQL");
		this.ndotdb = db;
	}

	public void run() {
		System.out.println("Select-SQL");
	}
}
