package org.ndot.date;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� SimpleDateFormatTest.java
 * 
 *<P>
 * 
 * �� ��:
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-12-26
 * 
 */
public class SimpleDateFormatTest {
	/**
	 * �ַ���ת��Ϊjava.util.Date<br>
	 * ֧�ָ�ʽΪ yyyy.MM.dd G 'at' hh:mm:ss z �� '2002-1-1 AD at 22:10:59 PSD'<br>
	 * yy/MM/dd HH:mm:ss �� '2002/1/1 17:55:00'<br>
	 * yy/MM/dd HH:mm:ss pm �� '2002/1/1 17:55:00 pm'<br>
	 * yy-MM-dd HH:mm:ss �� '2002-1-1 17:55:00' <br>
	 * yy-MM-dd HH:mm:ss am �� '2002-1-1 17:55:00 am' <br>
	 * 
	 * @param time
	 *            String �ַ���<br>
	 * @return Date ����<br>
	 */
	public static Date stringToDate(String time) {
		SimpleDateFormat formatter;
		int tempPos = time.indexOf("AD");
		time = time.trim();
		formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		if (tempPos > -1) {
			time = time.substring(0, tempPos) + "��Ԫ"
					+ time.substring(tempPos + "AD".length());// china
			formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		}
		tempPos = time.indexOf("-");
		if (tempPos > -1 && (time.indexOf(" ") < 0)) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
		} else if ((time.indexOf("/") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else if ((time.indexOf("-") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if ((time.indexOf("/") > -1) && (time.indexOf("am") > -1)
				|| (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		} else if ((time.indexOf("-") > -1) && (time.indexOf("am") > -1)
				|| (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		}
		ParsePosition pos = new ParsePosition(0);
		java.util.Date ctime = formatter.parse(time, pos);

		return ctime;
	}

	/**
	 * ��java.util.Date ��ʽת��Ϊ�ַ�����ʽ'yyyy-MM-dd HH:mm:ss'(24Сʱ��)<br>
	 * ��Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>
	 * 
	 * @param time
	 *            Date ����<br>
	 * @return String �ַ���<br>
	 */

	public static String dateToString(Date time) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = formatter.format(time);

		return ctime;
	}

	/**
	 * ��java.util.Date ��ʽת��Ϊ�ַ�����ʽ'yyyy-MM-dd HH:mm:ss a'(12Сʱ��)<br>
	 * ��Sat May 11 17:23:22 CST 2002 to '2002-05-11 05:23:22 ����'<br>
	 * 
	 * @param time
	 *            Date ����<br>
	 * @param x
	 *            int ���������磺1<br>
	 * @return String �ַ���<br>
	 */
	public static String dateToString(Date time, int x) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		String ctime = formatter.format(time);

		return ctime;
	}

	/**
	 *ȡϵͳ��ǰʱ��:����ֵֻΪ������ʽ 2002-10-30 20:24:39
	 * 
	 * @return String
	 */
	public static String Now() {
		return dateToString(new Date());
	}

	/**
	 *ȡϵͳ��ǰʱ��:����ֵֻΪ������ʽ 2002-10-30 08:28:56 ����
	 * 
	 * @param hour
	 *            Ϊ��������
	 *@return String
	 */
	public static String Now(int hour) {
		return dateToString(new Date(), hour);
	}

	/**
	 *ȡϵͳ��ǰʱ��:����ֵΪ������ʽ 2002-10-30
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD() {
		return dateToString(new Date()).substring(0, 10);

	}

	/**
	 *ȡϵͳ����ʱ��:����ֵΪ������ʽ 2002-10-30
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD(String date) {
		return date.substring(0, 10);

	}

	public static String getHour() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("H");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	public static String getDay() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("d");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	public static String getMonth() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("M");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	public static String getYear() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	public static String getWeek() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("E");
		String ctime = formatter.format(new Date());
		return ctime;
	}

}
