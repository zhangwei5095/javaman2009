package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class HelpContentAction extends Action {
	private NDotDB ndotdb;
	public HelpContentAction(NDotDB db){
		super("��������");
		this.ndotdb = db;
	}
	
	public void run(){
		System.out.println("��������");
	}
}
