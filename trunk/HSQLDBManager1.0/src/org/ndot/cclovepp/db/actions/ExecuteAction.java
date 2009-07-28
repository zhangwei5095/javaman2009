package org.ndot.cclovepp.db.actions;

import java.sql.SQLException;

import org.eclipse.jface.action.Action;
import org.ndot.cclovepp.db.NDotDB;

public class ExecuteAction extends Action {
private NDotDB ndotdb;
	
	public ExecuteAction(NDotDB db){
		super("Ö´ÐÐSQL(&Z)");
		this.ndotdb = db;
	}

public void run(){
	System.out.println("Ö´ÐÐSQL");
	String sql = ndotdb.getDocument().get();
	try {
		ndotdb.setRst(null);
		ndotdb.setRst(ndotdb.getSt().executeQuery(sql));
		ndotdb.setTableViewer();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	System.out.println(ndotdb.getDocument().get());
}
}
