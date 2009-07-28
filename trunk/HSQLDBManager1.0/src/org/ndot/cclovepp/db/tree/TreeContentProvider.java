package org.ndot.cclovepp.db.tree;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TreeContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement) {		
		return ((TreeElement)parentElement).getChildren().toArray();
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {			
		return ((TreeElement)element).hasChildren();
	}

	public Object[] getElements(Object inputElement) {
		return ((List)inputElement).toArray();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}
