package com.nasoft.security;

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
/*
	public static void main(String[] args) {
		//getPanByAcctString("12345678901234");
		String aTest = getPinBlock("111111","955800123456789102");
	}
*/	
	/**
	 * �����������������
	 * 
	 * @param mac
	 * @param tmp
	 * @return
	 */
	public static byte[] getNextMac(byte[] mac, byte[] tmp) {
		byte[] data = new byte[8];
		for (int i = 0; i < 8; i++) {
			data[i] = (byte) (mac[i] ^ tmp[i]);
		}
		return data;
	}

	/**
	 * ���
	 * 
	 * @param mac
	 * @param tmp
	 * @return
	 */
	public static byte[] getXor(byte[] mac, byte[] tmp) {
		int len = mac.length;
		byte[] data = new byte[len];
		for (int i = 0; i < len; i++) {
			data[i] = (byte) (mac[i] ^ tmp[i]);
		}
		return data;
	}

	/**
	 * �����ʺŵõ�PAN
	 * 
	 * @param acct
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPanByAcct(String acct) throws Exception {
		byte[] panArray = new byte[8];
		try {
			int acctLen = acct.length();
			String acctMain = acct.substring(acctLen - 13 > 0 ? acctLen - 13
					: 0, acctLen - 1);
			long intMain = Long.parseLong(acctMain);
			int i = 7;
			for (; i >= 0; i--) {
				long twoDigits = intMain % 100;
				intMain /= 100;
				panArray[i] = (byte) (((twoDigits / 10) << 4) + twoDigits % 10);

			}

		} catch (Exception e) {
			throw e;
		}
		return panArray;
	}
	
	/**
	 * �����ʺŵõ�PAN,String��ʽ
	 * @param acct
	 * @return
	 */
	public static String getPanByAcctString(String acct){
			int acctLen = acct.length();
			String acctMain = acct.substring(acctLen - 13 > 0 ? acctLen - 13
					: 0, acctLen - 1);
		return acctMain;
	}
	/**
	 * �����ʺź���������õ�PINBLOCK
	 * @param acct
	 * @param pin
	 * @return
	 */
	public static String getPinBlock(String pin, String acct)
	{
		String accNoTmp = getPanByAcctString(acct);
		String accNo = "0000" + accNoTmp;
		String strRes = "";
		String pinT = "";
		int iPinLen = pin.length();
		if(iPinLen < 10)
		{
			pinT += "0";
			pinT += Integer.toString(iPinLen);
			pinT += pin;
		}
		int iTem = pinT.length();
		if(iTem < 16)
			for(int i = 0;i< 16 - iTem; i++)
				pinT += "F";
		
		byte[] bResult = getXor(hex2Byte(pinT),hex2Byte(accNo));
		strRes = byte2hex(bResult);;
		return strRes;
	}
}
