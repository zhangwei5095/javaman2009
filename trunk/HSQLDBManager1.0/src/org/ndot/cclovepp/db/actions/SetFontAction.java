package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class SetFontAction extends Action {
	private NDotDB ndotdb;
	public SetFontAction(NDotDB db){
		super("设置字体");
		this.ndotdb = db;
	}
	
	public void run(){
		System.out.println("设置字体");
	}

}
