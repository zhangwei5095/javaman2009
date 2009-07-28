package org.ndot.cclovepp.db.tree;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.ndot.cclovepp.db.util.ImageFactory;

public class TreeLabelProvider implements ILabelProvider {

	public Image getImage(Object element) {
		boolean haschild = ((TreeElement) element).hasChildren();
		if (haschild) {
			return ImageFactory.loadImage(Display.getCurrent(),
					ImageFactory.TOC_CLOSED);
		} else {
			return ImageFactory.loadImage(Display.getCurrent(),
					ImageFactory.TOPIC);
		}
	}

	public String getText(Object element) {
		return ((TreeElement) element).getName();
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		ImageFactory.dispose();
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {

	}

}
