package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class DelectDBAction extends Action{

	private NDotDB ndotdb;
	
	public DelectDBAction(NDotDB db){
		super("ɾ��DB(&D)");
		this.ndotdb = db;
	}
	
	public void run(){
		 System.out.println("ɾ�������ݿ�����");
	}

}
