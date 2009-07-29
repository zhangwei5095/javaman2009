package org.ndot.ips.hsqldb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


/**
 * project: 数据交换
 * 
 * filename：TestMain.java
 * 
 * Function: 
 * 
 * Copyright: Copyright (c) 2008
 * 
 * @author 孙金城
 * @version 1.0
 * @Date 2007-12-27
 */

public class StartIPS {
	public static void main(String[] args) {
     try {
		String path = "ips/WEB-INF/";
		String config1 = path + "ipsApplicationContext.xml";
		ApplicationContext context = new FileSystemXmlApplicationContext(
				new String[] { config1 });
		} catch (Exception e) {
            e.printStackTrace();
		}
	}
}
