package org.ndot.cclovepp.db.util;

import org.eclipse.jface.resource.ImageRegistry;

public class RegistryFactory {
	private static ImageRegistry imageRegistry =null;
	private RegistryFactory(){};
	public static ImageRegistry getImageRegistryInstance(){
		if ( imageRegistry == null)
			imageRegistry = new ImageRegistry();
		return imageRegistry;
	}
}
