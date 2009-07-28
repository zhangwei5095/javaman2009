package org.ndot.cclovepp.db.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.ndot.cclovepp.db.NDotDB;


public class SaveAction extends Action {
	private NDotDB editor;
	public SaveAction(NDotDB editor) {
		super("±£´æ@Ctrl+S");
//		this.setImageDescriptor(ImageDescriptor.createFromFile(ResourceManager.class,"\\icons\\open.gif"));
		this.editor = editor;
	}
	@Override
	public void run() {
		editor.getEventManager().saveFile();
	}
}
