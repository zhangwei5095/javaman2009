package org.ndot.ips.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenDateTime {
	public static String getDateTime() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}
	
}
