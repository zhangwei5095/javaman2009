package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class NewDBAction extends Action {
	
	private NDotDB ndotdb;
	public NewDBAction(NDotDB db){
		super("�½�DB(&N)");
		this.ndotdb = db;
	}
	
	public void run(){
		System.out.println("�����µ����ݿ�");
	}

}
