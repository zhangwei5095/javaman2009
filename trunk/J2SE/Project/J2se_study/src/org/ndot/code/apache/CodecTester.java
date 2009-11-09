package org.ndot.code.apache;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� CodecTester.java
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
 * ����ʱ��: 2009-11-4
 * 
 */
public class CodecTester {
	@Test
	public void base64Str2HexStrTest() {
		// Base64������ַ���
		String base64Str = "FAWGGx0RaGAAAtsHOAAANgiAAAAP";
		// ��Base64�����ַ���ת��Ϊasc
		byte[] ascBytes = Base64.decodeBase64(base64Str);
		// ��ascת��ΪHEX
		char[] hexChars = Hex.encodeHex(ascBytes, false);
		System.out.println(new String(hexChars));
	}

	@Test
	public void hexStr2Base64Str() throws DecoderException {
		String hexStr = "1405861B1D1168600002DB0738000036088000000F";
		// ��Hex�ַ���ת��Ϊ ASC�ַ���
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
		String asc="�й� aa";
		String hexStr=new String(Hex.encodeHex(asc.getBytes()));
		System.out.println("["+asc+"] hex value is : "+hexStr);
		try {
			System.out.println(new String(Hex.decodeHex(hexStr.toCharArray())));
		} catch (DecoderException e) {
			e.printStackTrace();
		}
	}

}
