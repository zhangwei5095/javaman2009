package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class NewDBAction extends Action {
	
	private NDotDB ndotdb;
	public NewDBAction(NDotDB db){
		super("新建DB(&N)");
		this.ndotdb = db;
	}
	
	public void run(){
		System.out.println("创建新的数据库");
	}

}
