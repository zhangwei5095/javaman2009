package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.ndot.cclovepp.db.NDotDB;



public class OpenAction extends Action {
	private NDotDB editor;
	public OpenAction(NDotDB editor) {
		super("´ò¿ª@Ctrl+O");
//		this.setImageDescriptor(ImageDescriptor.createFromFile(ResourceManager.class,"icons\\open.gif"));
		this.editor = editor;
	}
	@Override
	public void run() {
		editor.getEventManager().openFile();
	}
}

