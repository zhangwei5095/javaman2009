package com.nasoft.securty;

public class StringUtil {
	/**
	 * ������byte������String������ʾ
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
	 * ��String�Ķ������ַ���ת������
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
	 * ��int�͵�����ת����byte���飬�ĸ��ֽ�
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
	 * ��byte����ת����int�ͣ�4���ֽڵ�����
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
	 * ��int������ת���������ֽڵ�����
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
	 * �������ֽڵ�����ת��int��
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
