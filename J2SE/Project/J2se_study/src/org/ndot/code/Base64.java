package org.ndot.code;

import sun.misc.BASE64Decoder;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： Base64.java
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
 * 创建时间: 2009-11-3
 * 
 */
public class Base64 {
	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	// 将 BASE64 编码的字符串 s 进行解码
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
