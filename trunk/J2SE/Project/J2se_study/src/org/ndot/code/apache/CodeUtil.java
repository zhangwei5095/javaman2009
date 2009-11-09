package org.ndot.code.apache;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： CodeUtil.java
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
public class CodeUtil {
	/**
	 * 
	 * <p>
	 * 功能: 将base64编码的字符串转化为asc字符串
	 * 
	 * 如：
	 * 
	 * 输入：FAWGGx0RaGAAAtsHOAAANgiAAAAP
	 * 输出：1405861B1D1168600002DB0738000036088000000F
	 * 
	 * <p>
	 * 
	 * @param base64str
	 *            base64编码的字符串 如：FAWGGx0RaGAAAtsHOAAANgiAAAAP
	 * @return <p>
	 *         作者:孙金城
	 *         <p>
	 */
	public static String base642Hex(String base64str) {
		// 将Base64编码字符串转化为asc
		byte[] ascBytes = Base64.decodeBase64(base64str);
		// 将asc转化为HEX
		char[] hexChars = Hex.encodeHex(ascBytes, false);
		return new String(hexChars);
	}

	/**
	 * 
	 * <p>
	 * 功能: 将Hex编码的字符串转换为Base64编码的字符串
	 * 
	 * 如：
	 * 
	 * 输入：1405861B1D1168600002DB0738000036088000000F
	 * 输出：FAWGGx0RaGAAAtsHOAAANgiAAAAP
	 * 
	 * <p>
	 * 
	 * @param hexStr
	 *            Hex编码的字符串
	 * @return
	 * @throws DecoderException
	 *             <p>
	 *             作者:孙金城
	 *             <p>
	 */
	public static String hex2Base64(String hexStr) throws DecoderException {
		// 将Hex字符串转化为 ASC字符串
		byte[] ascBytes = Hex.decodeHex(hexStr.toCharArray());
		// 将asc转化为base64
		String base64str = Base64.encodeBase64String(ascBytes);
		return base64str;
	}
}
