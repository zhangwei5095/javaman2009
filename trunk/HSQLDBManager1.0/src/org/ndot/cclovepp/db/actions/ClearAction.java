package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class ClearAction extends Action {
private NDotDB ndotdb;
	
	public ClearAction(NDotDB db){
		super("���SQL(&C)");
		this.ndotdb = db;
	}

public void run(){
	System.out.println("���");
}
}
