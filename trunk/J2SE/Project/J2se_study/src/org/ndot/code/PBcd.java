package org.ndot.code;

import java.io.ByteArrayOutputStream;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 *
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 *
 * �ļ����� PBcd.java
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
 * ����ʱ��: 2009-10-21
 * 
 */
public class PBcd {
	public static void main(String[] args) {
		  byte[] bcd = str2cbcd("01234567897658935260");
		  for (int i = 0; i < bcd.length; i++) {
		   System.out.println(bcd[i]);
		  }
		  System.out.println(cbcd2string(bcd));
		 }

		 public static byte[] str2cbcd(String s) {
		  if (s.length() % 2 != 0) {
		   s = "0" + s;
		  }
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  char[] cs = s.toCharArray();
		  for (int i = 0; i < cs.length; i += 2) {
		   int high = cs[i] - 48;
		   int low = cs[i + 1] - 48;
		   baos.write(high << 4 | low);
		  }
		  return baos.toByteArray();
		 }

		 public static String cbcd2string(byte[] b) {
		  StringBuffer sb = new StringBuffer();
		  for (int i = 0; i < b.length; i++) {
		   int h = ((b[i]&0xff) >> 4) + 48;
		   sb.append((char) h);
		   int l = (b[i] & 0x0f) + 48;
		   sb.append((char) l);
		  }
		  return sb.toString();
		 }



}
