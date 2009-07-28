package org.ndot.cclovepp.db.document.resource;

import java.io.IOException;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.Color;
import org.ndot.cclovepp.db.util.Constants;


public class ResourceManager {
	
	private ResourceManager(){}//设置为private，不允许创建对象
	private static ColorRegistry colorRegistry;//管理颜色的对象
	//获得颜色注册器对象
	public static ColorRegistry getColorRegistry() {
		if (colorRegistry == null) {
			colorRegistry = new ColorRegistry();
			initColor();
		}
		return colorRegistry;
	}
	//获得颜色注册器中的颜色对象的工具方法
	public static Color getColor( String key ) {
		Color color = getColorRegistry().get(key);
		return color;
	}
	//初始话首选项文件中设置的各种代码的颜色
	private static void initColor() {
		colorRegistry.put(Constants.COLOR_COMMENT,StringConverter.asRGB(getPreferenceStore().getString(Constants.COLOR_COMMENT)));
		colorRegistry.put(Constants.COLOR_KEYWORD,StringConverter.asRGB(getPreferenceStore().getString(Constants.COLOR_KEYWORD)));
		colorRegistry.put(Constants.COLOR_STRING,StringConverter.asRGB("128,128,64"));
		colorRegistry.put(Constants.COLOR_OBJECT,StringConverter.asRGB(getPreferenceStore().getString(Constants.COLOR_KEYWORD)));
	}
	//保存的首选项设置文件
	private static PreferenceStore preference ;
	//加载首选项文件
	public static PreferenceStore getPreferenceStore() {
		if (preference == null) {
			preference = new PreferenceStore( "sqlEditor.properties");
			try {
				preference.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return preference;
	}
}
