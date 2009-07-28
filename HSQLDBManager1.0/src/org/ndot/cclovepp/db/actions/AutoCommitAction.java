package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class AutoCommitAction extends Action {
	private NDotDB ndotdb;

	public AutoCommitAction(NDotDB db) {
		super("自动提交模式",Action.AS_CHECK_BOX);
		this.ndotdb = db;
		this.setChecked(true);
	}

	public void run() {
		System.out.println("Auto Commit model");
	}
}
