package org.ndot.beanutils;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.time.DateUtils;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： DateConverter.java
 * 
 *<P>
 * 
 * 功 能:
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-12-26
 * 
 */
public class DateConverter implements Converter {

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Date) {
			return value;
		}

		if (value instanceof Long) {
			Long longValue = (Long) value;
			return new Date(longValue.longValue());
		}
		if (value instanceof String) {
			Date endTime = null;
			try {
				endTime = DateUtils.parseDate(value.toString(), new String[] {
						"yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss",
						"yyyy-MM-dd HH:mm", "yyyy-MM-dd" });
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return endTime;
		}

		return null;
	}

}
