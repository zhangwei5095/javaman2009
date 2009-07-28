package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class CommitAction extends Action {
	private NDotDB ndotdb;
	public CommitAction(NDotDB db){
		super("提交事务");
		this.ndotdb = db;	
	}
	
	public void run(){
		System.out.println("提交事务");
	}
}
