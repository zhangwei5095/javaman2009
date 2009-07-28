package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.ndot.cclovepp.db.NDotDB;

public class ExitAction extends Action {

private NDotDB ndotdb;
	
	public ExitAction(NDotDB db){
		super("ÍË³ö(&E)");
		this.ndotdb = db;
	}

public void run(){
	System.out.println("ÍË³ö");
	ndotdb.close();
}


}
