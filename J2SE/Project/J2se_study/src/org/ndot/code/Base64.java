package org.ndot.code;

import sun.misc.BASE64Decoder;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� Base64.java
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
 * ����ʱ��: 2009-11-3
 * 
 */
public class Base64 {
	// �� s ���� BASE64 ����
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	// �� BASE64 ������ַ��� s ���н���
	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		String base64Str = "FAWGGx0RaGAAAtsHOAAANgiAAAAPogHgAAn0tGoAACUfEsAAIlVNQAAAns1aAACYDoigABHKuzgAAGwUkYAATGkUoAAIxFvQAAEpDwxAAAPgB3AABIJ7wgAAEUxIYAARAZ70AAIxTV2AAAWKXDAAAlBwSgABTp2OwAAKBJ2oAAQeOdAAAAEBGmAAEIkpMAACABfjAAAAkbU=";
		String ascStr = Base64.getFromBASE64(base64Str);
		
		System.out.println(ascStr);
	}

}
