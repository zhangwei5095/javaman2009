package org.ndot.code.apache;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� CodeUtil.java
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
public class CodeUtil {
	/**
	 * 
	 * <p>
	 * ����: ��base64������ַ���ת��Ϊasc�ַ���
	 * 
	 * �磺
	 * 
	 * ���룺FAWGGx0RaGAAAtsHOAAANgiAAAAP
	 * �����1405861B1D1168600002DB0738000036088000000F
	 * 
	 * <p>
	 * 
	 * @param base64str
	 *            base64������ַ��� �磺FAWGGx0RaGAAAtsHOAAANgiAAAAP
	 * @return <p>
	 *         ����:����
	 *         <p>
	 */
	public static String base642Hex(String base64str) {
		// ��Base64�����ַ���ת��Ϊasc
		byte[] ascBytes = Base64.decodeBase64(base64str);
		// ��ascת��ΪHEX
		char[] hexChars = Hex.encodeHex(ascBytes, false);
		return new String(hexChars);
	}

	/**
	 * 
	 * <p>
	 * ����: ��Hex������ַ���ת��ΪBase64������ַ���
	 * 
	 * �磺
	 * 
	 * ���룺1405861B1D1168600002DB0738000036088000000F
	 * �����FAWGGx0RaGAAAtsHOAAANgiAAAAP
	 * 
	 * <p>
	 * 
	 * @param hexStr
	 *            Hex������ַ���
	 * @return
	 * @throws DecoderException
	 *             <p>
	 *             ����:����
	 *             <p>
	 */
	public static String hex2Base64(String hexStr) throws DecoderException {
		// ��Hex�ַ���ת��Ϊ ASC�ַ���
		byte[] ascBytes = Hex.decodeHex(hexStr.toCharArray());
		// ��ascת��Ϊbase64
		String base64str = Base64.encodeBase64String(ascBytes);
		return base64str;
	}
}
