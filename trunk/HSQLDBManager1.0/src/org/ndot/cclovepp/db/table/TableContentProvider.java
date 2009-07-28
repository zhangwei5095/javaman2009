package org.ndot.cclovepp.db.table;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TableContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		return ((List)inputElement).toArray();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
