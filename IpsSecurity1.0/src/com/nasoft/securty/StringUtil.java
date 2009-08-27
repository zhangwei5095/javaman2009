package com.nasoft.securty;

public class StringUtil {
	/**
	 * 二进制byte数组用String串来表示
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}

		}
		return hs.toUpperCase();
	}

	/**
	 * 将String的二进制字符串转成数组
	 * 
	 * @param b
	 * @return
	 */
	public static final byte[] hex2Byte(String b) {
		char data[] = b.toCharArray();
		int l = data.length;
		byte out[] = new byte[l >> 1];
		int i = 0;
		for (int j = 0; j < l;) {
			int f = Character.digit(data[j++], 16) << 4;
			f |= Character.digit(data[j++], 16);
			out[i] = (byte) (f & 0xff);
			i++;
		}
		return out;
	}

	/**
	 * 将int型的数据转换成byte数组，四个字节
	 * 
	 * @param intValue
	 * @return
	 */
	public static byte[] int2Byte(int intValue) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
		}
		return b;
	}

	/**
	 * 将byte数组转换成int型，4个字节的数组
	 * 
	 * @param b
	 * @return
	 */
	public static int byte2Int(byte[] b) {
		int intValue = 0, tempValue = 0xFF;
		for (int i = 0; i < b.length; i++) {
			intValue += (b[i] & tempValue) << (8 * (3 - i));
		}
		return intValue;
	}

	/**
	 * 将int型数字转换成两个字节的数组
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] short2byte(int n) {
		byte b[] = new byte[2];
		b[0] = (byte) (n >> 8);
		b[1] = (byte) n;
		return b;
	}

	/**
	 * 将两个字节的数组转成int型
	 * @param b
	 * @return
	 */
	public static int byte2short(byte[] b) {
		return (b[1] & 0xFF) + ((((short) b[0]) << 8) & 0xFF00);
	}

	public static void main(String[] args) {
		System.out.println(byte2hex("A".getBytes()));
	}
}
