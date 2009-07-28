package org.ndot.cclovepp.db.actions;

import java.sql.SQLException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.ndot.cclovepp.db.NDotDB;
import org.ndot.cclovepp.db.tree.TreeElement;

public class ShowTabDataAction extends Action {
	private NDotDB ndotdb;
	public ShowTabDataAction(NDotDB db){
		super("��ʾ�������");
		this.ndotdb = db;
	}
	
	public void run(){
		StructuredSelection select = (StructuredSelection) ndotdb.getDbconnectTreeViewer().getSelection();
		TreeElement element = (TreeElement) select.getFirstElement();		
		String name = element.getName();
	
		if(name.contains("PUBLIC.")){
			String sql = "select * from "+name+" as t";
			ndotdb.getDocument().set(sql);
			try {
				ndotdb.setRst(null);
				ndotdb.setRst(ndotdb.getSt().executeQuery(sql));
				ndotdb.setTableViewer();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			MessageDialog.openConfirm(ndotdb.getShell(), "������ʾ",
			"��ѡ����ȷ�����ݱ����ýڵ�:)");
		}
		
	}
}
