package org.ndot.code.apache;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： CodecTester.java
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
 * 创建时间: 2009-11-4
 * 
 */
public class CodecTester {
	@Test
	public void base64Str2HexStrTest() {
		// Base64编码的字符串
		String base64Str = "FAWGGx0RaGAAAtsHOAAANgiAAAAP";
		// 将Base64编码字符串转化为asc
		byte[] ascBytes = Base64.decodeBase64(base64Str);
		// 将asc转化为HEX
		char[] hexChars = Hex.encodeHex(ascBytes, false);
		System.out.println(new String(hexChars));
	}

	@Test
	public void hexStr2Base64Str() throws DecoderException {
		String hexStr = "1405861B1D1168600002DB0738000036088000000F";
		// 将Hex字符串转化为 ASC字符串
		byte[] ascBytes = Hex.decodeHex(hexStr.toCharArray());

		byte[] base64bytes = Base64.encodeBase64(ascBytes, false);

		System.out.println(new String(base64bytes));

		String base64str = Base64.encodeBase64String(ascBytes);
		System.out.println(base64str);
		System.out.println(System.nanoTime());
		System.out.println(System.currentTimeMillis());
	}
	
	
	@Test
	public void str2hex(){
		String asc="中国 aa";
		String hexStr=new String(Hex.encodeHex(asc.getBytes()));
		System.out.println("["+asc+"] hex value is : "+hexStr);
		try {
			System.out.println(new String(Hex.decodeHex(hexStr.toCharArray())));
		} catch (DecoderException e) {
			e.printStackTrace();
		}
	}

}
