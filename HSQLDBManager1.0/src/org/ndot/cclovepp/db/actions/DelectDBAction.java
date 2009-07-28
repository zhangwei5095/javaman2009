package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class DelectDBAction extends Action{

	private NDotDB ndotdb;
	
	public DelectDBAction(NDotDB db){
		super("É¾³ýDB(&D)");
		this.ndotdb = db;
	}
	
	public void run(){
		 System.out.println("É¾³ý¸ÃÊý¾Ý¿âÅäÖÃ");
	}

}
