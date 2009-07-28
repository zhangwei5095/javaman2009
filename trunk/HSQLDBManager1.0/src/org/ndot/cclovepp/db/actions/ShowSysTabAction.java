package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class ShowSysTabAction extends Action {

	private NDotDB ndotdb;
	public ShowSysTabAction(NDotDB db){
		super("显示系统表(&S)",Action.AS_CHECK_BOX);
		this.ndotdb = db;
	}
	
	public void run(){
		System.out.println("显示系统数据表");
	}
}
