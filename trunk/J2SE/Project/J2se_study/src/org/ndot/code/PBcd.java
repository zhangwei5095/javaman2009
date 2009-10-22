package org.ndot.code;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.ndot.ISOUtil;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： PBcd.java
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
 * 创建时间: 2009-10-21
 * 
 */
public class PBcd {
	public static void main(String[] args) {
		String asc = "00601000152566210210331700002870   00000000000000006952120120401000000000010100C221030121000006018    02000152586210210331700002870   00000000000000006962120120401000000000000100D221030121000006018    03000152676210210331700002870   00000000000000006972120120401000000000000100D221030121000006018    04000152696210210331700002870   00000000000000006942120120401000000000000300C221030121000006018    05000152716210210331700002870   00000000000000006902120120401000000000000400C221030121000006018    06000152806210210331700002870   00000000000000006912120120401000000000000100D221030121000006018    ";
//		asc = asc.replace(" ", "0");
//		byte[] bcd = asc2cbcd(asc);
//		System.out.println("asc_length:"+asc.length());
//		System.out.println("asc_data:\n"+asc);
//		System.out.println("bcd_length:"+bcd.length);
//		System.out.println("NDot--bcd hex:\n"+ISOUtil.byte2HexStr(bcd, bcd.length));
//		System.out.println(cbcd2asc(bcd));
		
		
		
		
		
		byte[] bcdbyte = str2bcd(asc, true);
		System.out.println("bcdByte_length:"+bcdbyte.length);
		System.out.println("bcd hex:\n"+ISOUtil.byte2HexStr(bcdbyte, bcdbyte.length));
		
		String b2asc = bcd2str(bcdbyte, 0, bcdbyte.length*2, true);
		System.out.println("asc_data:\n"+asc);
		System.out.println("b2asc is:\n"+b2asc);
	}

	/**
	 * converts a BCD representation of a number to a String
	 * 
	 * @param b
	 *            - BCD representation
	 * @param offset
	 *            - starting offset
	 * @param len
	 *            - BCD field len
	 * @param padLeft
	 *            - was padLeft packed?
	 * @return the String representation of the number
	 */
	public static String bcd2str(byte[] b, int offset, int len, boolean padLeft) {
		StringBuffer d = new StringBuffer(len);
		int start = (((len & 1) == 1) && padLeft) ? 1 : 0;
		for (int i = start; i < len + start; i++) {
			int shift = ((i & 1) == 1 ? 0 : 4);
			char c = Character.forDigit(
					((b[offset + (i >> 1)] >> shift) & 0x0F), 16);
			if (c == 'd')
				c = '=';
			d.append(Character.toUpperCase(c));
		}
		return d.toString();
	}

	/**
	 * converts to BCD
	 * 
	 * @param s
	 *            - the number
	 * @param padLeft
	 *            - flag indicating left/right padding
	 * @return BCD representation of the number
	 */
	public static byte[] str2bcd(String s, boolean padLeft) {
		int len = s.length();
		byte[] d = new byte[(len + 1) >> 1];
		return str2bcd(s, padLeft, d, 0);
	}

	/**
	 * converts to BCD
	 * 
	 * @param s
	 *            - the number
	 * @param padLeft
	 *            - flag indicating left/right padding
	 * @param fill
	 *            - fill value
	 * @return BCD representation of the number
	 */
	public static byte[] str2bcd(String s, boolean padLeft, byte fill) {
		int len = s.length();
		byte[] d = new byte[(len + 1) >> 1];
		Arrays.fill(d, fill);
		int start = (((len & 1) == 1) && padLeft) ? 1 : 0;
		for (int i = start; i < len + start; i++)
			d[i >> 1] |= (s.charAt(i - start) - '0') << ((i & 1) == 1 ? 0 : 4);
		return d;
	}

	/**
	 * converts to BCD
	 * 
	 * @param s
	 *            - the number
	 * @param padLeft
	 *            - flag indicating left/right padding
	 * @param d
	 *            The byte array to copy into.
	 * @param offset
	 *            Where to start copying into.
	 * @return BCD representation of the number
	 */
	public static byte[] str2bcd(String s, boolean padLeft, byte[] d, int offset) {
		int len = s.length();
		int start = (((len & 1) == 1) && padLeft) ? 1 : 0;
		for (int i = start; i < len + start; i++)
			d[offset + (i >> 1)] |= (s.charAt(i - start) - '0') << ((i & 1) == 1 ? 0: 4);
		return d;
	}

	/**
	 * 
	 * @param s
	 * @return <p>
	 *         功能: 将字符串转换为 压缩的BCD码
	 * 
	 *         <p>
	 *         作者:孙金城
	 *         <p>
	 */
	public static byte[] asc2cbcd(String s) {
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

	/**
	 * 
	 * @param b
	 * @return <p>
	 *         功能: 将压缩的BCD码 转化为 字符串
	 * 
	 *         <p>
	 *         作者:孙金城
	 *         <p>
	 */
	public static String cbcd2asc(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int h = ((b[i] & 0xff) >> 4) + 48;
			sb.append((char) h);
			int l = (b[i] & 0x0f) + 48;
			sb.append((char) l);
		}
		return sb.toString();
	}

}
