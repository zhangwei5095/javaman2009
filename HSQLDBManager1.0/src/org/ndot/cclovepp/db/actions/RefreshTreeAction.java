package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class RefreshTreeAction extends Action {
	private NDotDB ndotdb;

	public RefreshTreeAction(NDotDB db) {
		super("ˢ����(&R)");
		this.ndotdb = db;
	}

	public void run() {
		System.out.println("ˢ��");
	}

}
