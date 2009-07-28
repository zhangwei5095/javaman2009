package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class ShowScheams extends Action {
	private NDotDB ndotdb;
	public ShowScheams(NDotDB db){
		super("显示表空间(&M)",Action.AS_CHECK_BOX);
		this.ndotdb = db;
		this.setChecked(true);
	}
	
	public void run(){
		System.out.println("显示表空间");
	}

}
