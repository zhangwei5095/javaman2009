package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class AboutAction extends Action {
	private NDotDB ndotdb;
	public AboutAction(NDotDB db){
		super("关于");
		this.ndotdb = db;
	}
	
	public void run(){
		System.out.println("关于");
	}
}
