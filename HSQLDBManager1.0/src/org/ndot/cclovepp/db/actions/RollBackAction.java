package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class RollBackAction extends Action {
	private NDotDB ndotdb;
	public RollBackAction(NDotDB db){
		super("�ع�����");
		this.ndotdb = db;
	}
	
	public void run(){
		System.out.println("�ع�����");
	}
}
