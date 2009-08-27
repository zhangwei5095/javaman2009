package com.nasoft.securty;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class HardwareCryp {

	public static void main(String[] args) {
		try {
			// System.out.println(sendCmdD10F("10.1.11.102", "6666", "5", "8",
			// "8", "A5A94F22B48A7988A5A94F22B48A7988", "A502016BD4B777CC"));

			String ip = "10.0.7.99";
			String port = "6666";
			String mak = "1234567890ABCDEF";
			String macbuff = "123456786868";
			String timeout = "2";
			String bankIndex = "666";
			String key = "1234567890ABCDEF1234567890ABCDEF";

			// //
			HardwareCryp.saveMmk(ip, port, timeout, bankIndex, key);

			HardwareCryp.sendCmdD10D(ip, port, timeout, bankIndex, "16");

			// String data="532887194FA5942E";
			// String pinkey="CCC4A9F37CAAFE90111AB4679C41576D";
			// String pin= HardwareCryp.getPin(ip, port, timeout, pinkey, data,
			// "0");
			// System.out.println("ResultMac="+pin);

			// System.out.println(StringUtil.byte2hex("12345678".getBytes()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ����MacBuff����Mac
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param mak
	 *            mac��Կ����(H)
	 * @param macbuff
	 *            macbuff(H)
	 * @return
	 */
	public static String getMac(String ip, String port, String timeout,
			String mak, String macbuff) throws Exception {
		String result = sendCmdD132(ip, port, timeout, mak, macbuff);
		return result.startsWith("41") ? result.substring(2, 10) : result;
	}

	/**
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param pik1
	 *            ԭ����pin��Կ(H)
	 * @param pik2
	 * @param acctNo
	 * @param oriPinValue
	 * @param oriAcctFlag
	 * @param destAcctFlag
	 * @return
	 */
	public static String pinTransfer(String ip, String port, String timeout,
			String pik1, String pik2, String acctNo, String oriPinValue,
			String oriAcctFlag, String destAcctFlag) throws Exception {
		try {
			String oriPinBlock = getPin(ip, port, timeout, pik1, oriPinValue,
					"0").substring(2);

			byte[] oriPinArray = new byte[8];
			if ("1".equals(oriAcctFlag)) {
				byte[] panArray = CrypTool.getPanByAcct(acctNo);
				oriPinArray = CrypTool.getNextMac(StringUtil
						.hex2Byte(oriPinBlock), panArray);
			} else {
				oriPinArray = StringUtil.hex2Byte(oriPinBlock);
			}

			byte[] destBlockArray = new byte[8];
			if ("1".equals(destAcctFlag)) {
				byte[] panArray = CrypTool.getPanByAcct(acctNo);
				destBlockArray = CrypTool.getNextMac(oriPinArray, panArray);
			} else {
				destBlockArray = oriPinArray;
			}
			String destPinValue = getPin(ip, port, timeout, pik2,
					StringUtil.byte2hex(destBlockArray), "1").substring(2);
			return destPinValue;

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * ʹ��MAC���ģ�������Կ���ܵģ�������MAC
	 * 
	 * @param ip
	 * @param port
	 * @param timeout
	 * @param mak
	 * @param macbuff
	 * @param bankIndex
	 * @return
	 */
	public static String getMac(String ip, String port, String timeout,
			String mak, String macbuff, String bankIndex) throws Exception {
		int indexLen = bankIndex.length();
		String strLen = "16";

		String macKey = getKey(ip, port, timeout, strLen, bankIndex, "12", mak);

		String mac = getMac(ip, port, timeout, macKey, macbuff);
		return mac;
	}

	/**
	 * ������Կʱ��ʹ��
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param mak
	 *            mac��Կ����(H)
	 * @param macbuff
	 *            macbuff(H)
	 * @return
	 */
	public static String checkMac(String ip, String port, String timeout,
			String mak, String macbuff) throws Exception {
		String mac = sendCmdD132(ip, port, timeout, mak, macbuff);
		mac = mac.startsWith("41") ? mac.substring(0, 10) : mac;
		String result = sendCmdD132Tmp(ip, port, timeout, mak);
		if (result.startsWith("41")) {
			return mac.substring(2).concat(result.substring(2, 10));
		} else {
			return "4502";
		}
	}

	/**
	 * ���ܻ����PIN
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param pinkey
	 *            PIN��Կ,����(H)
	 * @param data
	 *            Ҫ����/���ܵ�����(H)
	 * @param flag
	 *            �ӽ��ܱ�־(1,���ܣ�0������)
	 * @return
	 */
	public static String getPin(String ip, String port, String timeout,
			String pinkey, String data, String flag) throws Exception {
		String cypt = "";
		if (pinkey.length() == 16) {
			cypt = "21";
		} else if (pinkey.length() == 32) {
			cypt = "22";
		} else if (pinkey.length() == 48) {
			cypt = "23";
		}
		String result = flag.equals("0") ? sendCmdD114(ip, port, timeout, cypt,
				pinkey, data) : sendCmdD112(ip, port, timeout, cypt, pinkey,
				data);
		return result.startsWith("41") ? "41".concat(result.substring(6))
				: result;
	}

	/**
	 * ��������Կת��Ϊ������Կ����
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param mmklen
	 *            ������Կ����(A)
	 * @param index
	 *            ������Կ������(A)
	 * @param keytype
	 *            ��Կ���� (01��ͨ������Կ;11��PIN������Կ;12��MAC������Կ; 13�����ݼ�����Կ;
	 *            21:CVV������Կ;22: PVV������Կ) *
	 * @param key
	 *            ��Կ,����
	 * @return
	 */
	public static String getKey(String ip, String port, String timeout,
			String mmklen, String index, String keytype, String key)
			throws Exception {
		String result = sendCmdD102(ip, port, timeout, mmklen, index, keytype,
				key);
		if (result.startsWith("41")) {
			byte len = StringUtil.hex2Byte(result.substring(2, 4))[0];
			return result.substring(4, 4 + len * 2);
		} else {
			return result;
		}
	}

	/**
	 * ������Կ�������ĵ�����
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param keytype
	 *            ��Կ����(A) (01��ͨ������Կ;11��PIN������Կ;12��MAC������Կ; 13�����ݼ�����Կ;
	 *            21:CVV������Կ;22: PVV������Կ)
	 * @param key
	 *            ������Կ(H)
	 * @return
	 */
	public static String encodeKey(String ip, String port, String timeout,
			String keytype, String key) throws Exception {
		key = StringUtil.byte2hex(key.getBytes());
		String result = sendCmdD108(ip, port, timeout, keytype, key);
		byte keylen = StringUtil.hex2Byte(result.substring(2, 4))[0];
		return result.substring(4, 4 + keylen * 2);
	}

	/**
	 * ������������Կ
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param index
	 *            ������Կ������(A)
	 * @param key
	 *            ������Կ����(H)
	 * @return 1����ɹ���0����ʧ��
	 */
	public static String saveMmk(String ip, String port, String timeout,
			String index, String key) throws Exception {

		String encodeResult = sendCmdD108(ip, port, timeout, "01", key);
		if (!encodeResult.startsWith("41")) {
			return "0";
		}

		byte keylen = StringUtil.hex2Byte(encodeResult.substring(2, 4))[0];
		String encodeKey = encodeResult.substring(4, 4 + keylen * 2);
		String encedeCheckValue = encodeResult.substring(4 + keylen * 2);
		String saveResult = HardwareCryp.sendCmdD10F(ip, port, timeout, "8",
				index, encodeKey, encedeCheckValue);

		if (saveResult.startsWith("41")) {
			return "1";
		}
		return "0";
	}

	/**
	 * ������������Կ���ܵ���Կת��Ϊ�ñ�������Կ���ܡ�
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            ���ܻ��˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param wklen
	 *            ͨ������Կ����(A)
	 * @param index
	 *            ͨ������Կ����(A)
	 * @param keytype
	 *            ������Կ����(H) (01��ͨ������Կ;11��PIN������Կ;12��MAC������Կ; 13�����ݼ�����Կ;
	 *            21:CVV������Կ;22: PVV������Կ)
	 * @param wk
	 *            ͨ������Կ���ܵĹ�����Կ(H)
	 * @return
	 */
	private static String sendCmdD102(String ip, String port, String timeout,
			String wklen, String index, String keytype, String wk)
			throws Exception {

		byte[] cmd = StringUtil.hex2Byte("D102"); // ��������
		byte[] bWk = StringUtil.hex2Byte(wk); // ������Կ������
		byte bWkeyLen = (byte) bWk.length; // ������Կ����
		byte bLkLen = Byte.parseByte(wklen); // ͨ����Կ����
		byte[] bIndex = StringUtil.short2byte(Integer.parseInt(index)); // ͨ������Կ����
		byte bType = StringUtil.hex2Byte(keytype)[0]; // ��Կ����

		byte[] data = new byte[7 + bWkeyLen];
		System.arraycopy(cmd, 0, data, 0, 2);
		data[2] = bWkeyLen;
		data[3] = bLkLen;
		System.arraycopy(bIndex, 0, data, 4, 2);
		data[6] = bType;
		System.arraycopy(bWk, 0, data, 7, bWkeyLen);

		return send(ip, port, timeout, data);
	}

	/**
	 * ���ñ�������Կ���ܵ���Կת��Ϊ����������Կ����
	 * 
	 * @param ip
	 * @param port
	 * @param timeout
	 * @param wklen
	 * @param index
	 * @param keytype
	 * @param wk
	 * @return
	 */
	/*
	 * private static String sendCmdD104(String ip, String port, String timeout,
	 * String wklen,String index, String keytype, String wk){ byte[] cmd =
	 * StringUtil.hex2Byte("D104"); //�������� byte[] bWk = StringUtil.hex2Byte(wk);
	 * //������Կ������ byte bWkeyLen = (byte)bWk.length; //������Կ���� byte bLkLen =
	 * Byte.parseByte(wklen); //ͨ����Կ���� byte[] bIndex =
	 * StringUtil.short2byte(Integer.parseInt(index)); //ͨ������Կ���� byte bType =
	 * StringUtil.hex2Byte(keytype)[0]; //��Կ����
	 * 
	 * byte[] data = new byte[7+bWkeyLen]; System.arraycopy(cmd, 0, data, 0, 2);
	 * data[2] = bWkeyLen; data[3] = bLkLen; System.arraycopy(bIndex, 0, data,
	 * 4, 2); data[6] = bType; System.arraycopy(bWk, 0, data, 7, bWkeyLen);
	 * 
	 * return send(ip, port, timeout, data); }
	 */
	/**
	 * ���ñ�������Կ���ܵ���Կת��Ϊ����������Կ���ܡ�
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            ���ܻ��˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param wklen
	 *            ͨ����Կ����(A)
	 * @param index
	 *            ͨ������Կ����(A)
	 * @param keytype
	 *            ������Կ����(H) (01��ͨ������Կ;11��PIN������Կ;12��MAC������Կ; 13�����ݼ�����Կ;
	 *            21:CVV������Կ;22: PVV������Կ)
	 * @param wk
	 *            ͨ������Կ���ܵĹ�����Կ(H)
	 * @return
	 */
	/*
	 * public static String sendCmdD104(String ip, String port, String timeout,
	 * String wklen, String index, String keytype, String wk) {
	 * 
	 * byte[] cmd = StringUtil.hex2Byte("D104"); //�������� byte[] bWk =
	 * StringUtil.hex2Byte(wk); //������Կ������ byte bWkeyLen = (byte)bWk.length;
	 * //������Կ���� byte bLkLen = Byte.parseByte(wklen); //ͨ����Կ���� byte[] bIndex =
	 * StringUtil.short2byte(Integer.parseInt(index)); //ͨ������Կ���� byte bType =
	 * StringUtil.hex2Byte(keytype)[0]; //��Ҫ����
	 * 
	 * byte[] data = new byte[7+bWkeyLen]; System.arraycopy(cmd, 0, data, 0, 2);
	 * data[2] = bWkeyLen; data[3] = bLkLen; System.arraycopy(bIndex, 0, data,
	 * 4, 2); data[6] = bType; System.arraycopy(bWk, 0, data, 7, bWkeyLen);
	 * 
	 * return send(ip, port, timeout, data); }
	 */
	/**
	 * ����ָ�����ȵ�������Կ�����ü��ܻ���������Կ���ܺ󷵻���������Կ��У����
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param type
	 *            ��Կ����(H) (01��ͨ������Կ;11��PIN������Կ;12��MAC������Կ; 13�����ݼ�����Կ;
	 *            21:CVV������Կ;22: PVV������Կ)
	 * @param key
	 *            ��Կ����(H)
	 * @return
	 */
	private static String sendCmdD108(String ip, String port, String timeout,
			String type, String key) throws Exception {
		byte[] keyByte = StringUtil.hex2Byte(key);
		byte len = (byte) keyByte.length;
		byte[] data = new byte[4 + len];
		byte[] cmd = StringUtil.hex2Byte("D108");
		System.arraycopy(cmd, 0, data, 0, 2); // ��������
		data[2] = len;
		data[3] = StringUtil.hex2Byte(type)[0];

		System.arraycopy(keyByte, 0, data, 4, len);
		return send(ip, port, timeout, data);
	}

	/**
	 * �÷����ϳ�MMK
	 * 
	 * @param ip
	 * @param port
	 * @param timeout
	 * @param data1
	 * @param data2
	 * @return
	 */
	/*
	 * private static String sendCmdD10C(String ip,String port,String timeout,
	 * String data1,String data2){ byte[] cmd = StringUtil.hex2Byte("D10C");
	 * byte keylen = (byte)16; byte num = (byte)2; byte keytype =
	 * StringUtil.hex2Byte("01")[0]; byte[] dataA = StringUtil.hex2Byte(data1);
	 * byte[] dataB = StringUtil.hex2Byte(data2); byte[] data = new byte[37];
	 * System.arraycopy(cmd, 0, data, 0, 2); data[2] = keylen; data[3] = num;
	 * data[4] = keytype; System.arraycopy(dataA, 0, data, 5, 16);
	 * System.arraycopy(dataB, 0, data, 21, 16); return send(ip, port, timeout,
	 * data); }
	 */
	/**
	 * ������������Կ,����LMK���ܡ�
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param index
	 *            ��������Կ������(H)
	 * @param len
	 *            ��Կ����(A)
	 * @return
	 */

	public static String sendCmdD10D(String ip, String port, String timeout,
			String index, String len) throws Exception {
		byte[] data = new byte[5];
		byte[] cmd = StringUtil.hex2Byte("D10D");
		byte[] bIndex = StringUtil.short2byte(Integer.parseInt(index));
		System.arraycopy(cmd, 0, data, 0, 2);
		System.arraycopy(bIndex, 0, data, 2, 2);
		data[4] = Byte.parseByte(len);
		return send(ip, port, timeout, data);
	}

	/**
	 * �洢��������Կ
	 * 
	 * @param ip
	 *            Ӳ�����ܻ�IP(A)
	 * @param port
	 *            Ӳ�����ܻ����Ŷ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param index
	 *            ��������Կ��������(H)
	 * @param cklen
	 *            У��ֵ����
	 * @param key
	 *            ������Կ���ܵ���������Կ(H)
	 * @return
	 */

	private static String sendCmdD10F(String ip, String port, String timeout,
			String checklen, String index, String key, String check)
			throws Exception {
		byte[] cmd = StringUtil.hex2Byte("D10F");
		byte[] bKey = StringUtil.hex2Byte(key);
		byte bLen = (byte) bKey.length;
		byte[] bIndex = StringUtil.short2byte(Integer.parseInt(index));
		byte cklen = Byte.parseByte(checklen);
		byte[] checkvalue = StringUtil.hex2Byte(check);
		byte[] data = new byte[6 + bLen + cklen];
		System.arraycopy(cmd, 0, data, 0, 2);
		data[2] = bLen;
		System.arraycopy(bIndex, 0, data, 3, 2);
		data[5] = cklen;
		System.arraycopy(bKey, 0, data, 6, bLen);
		System.arraycopy(checkvalue, 0, data, 6 + bLen, cklen);
		return send(ip, port, timeout, data);
	}

	/**
	 * ��������Կ�����ݽ��м���
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP��ַ(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param cypt
	 *            ���ܷ�ʽ(H)
	 *            (11��DES_ECB;21��DES_CBC;12��3DES_ECB(˫����);22��3DES_ECB��˫���ȣ�
	 *            13��3DES_ECB��3���ȣ�;23��3DES_ECB��3���ȣ�)
	 * @param key
	 *            LMK���ܵ���Կ(H)
	 * @param encyptdata
	 *            ��Ҫ���ܵ�����(H)
	 * @return
	 */
	private static String sendCmdD112(String ip, String port, String timeout,
			String cypt, String key, String encyptdata) throws Exception {
		byte[] cmd = StringUtil.hex2Byte("D112");
		byte bCypt = StringUtil.hex2Byte(cypt)[0];
		byte[] bKey = StringUtil.hex2Byte(key);
		int keylen = bKey.length;
		byte[] bData = StringUtil.hex2Byte(encyptdata);
		int length = bData.length;
		byte[] bLen = StringUtil.short2byte(length);
		byte[] data = new byte[5 + keylen + length];
		System.arraycopy(cmd, 0, data, 0, 2);
		data[2] = bCypt;
		System.arraycopy(bKey, 0, data, 3, keylen);
		System.arraycopy(bLen, 0, data, 3 + keylen, 2);
		System.arraycopy(bData, 0, data, 5 + keylen, length);
		return send(ip, port, timeout, data);
	}

	/**
	 * ��������Կ�����ݽ��н���
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP��ַ(A)
	 * @param port
	 *            Ӳ�����ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param cypt
	 *            ���ܷ�ʽ(H)
	 *            (11��DES_ECB;21��DES_CBC;12��3DES_ECB(˫����);22��3DES_ECB��˫���ȣ�
	 *            13��3DES_ECB��3���ȣ�;23��3DES_ECB��3���ȣ�)
	 * @param key
	 *            LMK���ܵ���Կ(H)
	 * @param encyptdata
	 *            ��Ҫ���ܵ�����(H)
	 * @return
	 */
	private static String sendCmdD114(String ip, String port, String timeout,
			String cypt, String key, String encyptdata) throws Exception {
		byte[] cmd = StringUtil.hex2Byte("D114");
		byte bCypt = StringUtil.hex2Byte(cypt)[0];
		byte[] bKey = StringUtil.hex2Byte(key);
		int keylen = bKey.length;
		byte[] bData = StringUtil.hex2Byte(encyptdata);
		int length = bData.length;
		byte[] bLen = StringUtil.short2byte(length);

		byte[] data = new byte[5 + keylen + bData.length];
		System.arraycopy(cmd, 0, data, 0, 2);
		data[2] = bCypt;
		System.arraycopy(bKey, 0, data, 3, keylen);
		System.arraycopy(bLen, 0, data, 3 + keylen, 2);
		System.arraycopy(bData, 0, data, 5 + keylen, bData.length);
		return send(ip, port, timeout, data);
	}

	/**
	 * �������MAK���������ݼ���MAC
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP��ַ(A)
	 * @param port
	 *            ���ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param mak
	 *            MAK����(H)
	 * @param endata
	 *            ����(H)
	 * @return
	 */
	private static String sendCmdD132(String ip, String port, String timeout,
			String mak, String endata) throws Exception {
		byte[] cmd = StringUtil.hex2Byte("D132");
		byte bCypt = StringUtil.hex2Byte("02")[0];
		byte[] bMak = StringUtil.hex2Byte(mak);
		byte bMaklen = (byte) bMak.length;

		byte[] xl = StringUtil.hex2Byte("0000000000000000");
		byte[] bEndata = endata.getBytes();
		byte[] bEndatalen = StringUtil.short2byte(bEndata.length);

		byte[] data = new byte[14 + bMaklen + bEndata.length];
		System.arraycopy(cmd, 0, data, 0, 2);
		data[2] = bCypt;
		data[3] = bMaklen;
		System.arraycopy(bMak, 0, data, 4, bMaklen);
		System.arraycopy(xl, 0, data, 4 + bMaklen, 8);
		System.arraycopy(bEndatalen, 0, data, 12 + bMaklen, 2);
		System.arraycopy(bEndata, 0, data, 14 + bMaklen, bEndata.length);

		return send(ip, port, timeout, data);
	}

	/**
	 * ��8��0x00����mac���㣬����������Կ
	 * 
	 * @param ip
	 *            Ӳ�����ܻ���IP��ַ(A)
	 * @param port
	 *            ���ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param mak
	 *            MAK����(H)
	 * @return
	 */
	private static String sendCmdD132Tmp(String ip, String port,
			String timeout, String mak) throws Exception {
		byte[] cmd = StringUtil.hex2Byte("D132");
		byte bCypt = StringUtil.hex2Byte("02")[0];
		byte[] bMak = StringUtil.hex2Byte(mak);
		byte bMaklen = (byte) bMak.length;

		byte[] xl = StringUtil.hex2Byte("0000000000000000");
		byte[] bEndata = new byte[8];
		Arrays.fill(bEndata, (byte) 0x00);
		byte[] bEndatalen = StringUtil.short2byte(bEndata.length);

		byte[] data = new byte[14 + bMaklen + bEndata.length];
		System.arraycopy(cmd, 0, data, 0, 2);
		data[2] = bCypt;
		data[3] = bMaklen;
		System.arraycopy(bMak, 0, data, 4, bMaklen);
		System.arraycopy(xl, 0, data, 4 + bMaklen, 8);
		System.arraycopy(bEndatalen, 0, data, 12 + bMaklen, 2);
		System.arraycopy(bEndata, 0, data, 14 + bMaklen, bEndata.length);

		return send(ip, port, timeout, data);
	}

	/**
	 * У�� MAC
	 * 
	 * @param ip
	 *            ���ܻ���IP��ַ(A)
	 * @param port
	 *            ���ܻ��Ķ˿�(A)
	 * @param timeout
	 *            ��ʱʱ��(A)
	 * @param mak
	 *            MAK��Կ������(H)
	 * @param mac
	 *            �������mac����
	 * @param endata
	 *            macbuff����
	 * @return
	 */
	/*
	 * private static String sendCmdD134(String ip,String port,String timeout,
	 * String mak,String mac,String endata){ byte[] cmd =
	 * StringUtil.hex2Byte("D134"); byte bCypt = StringUtil.hex2Byte("02")[0];
	 * byte[] bMak = StringUtil.hex2Byte(mak); byte bMakLen = (byte)bMak.length;
	 * byte[] xl = StringUtil.hex2Byte("0000000000000000"); byte[] bMac =
	 * StringUtil.hex2Byte(mac); byte[] bEndata = endata.getBytes(); byte[]
	 * enlen = StringUtil.short2byte(bEndata.length); byte[] data = new byte[18 +
	 * bMakLen + bEndata.length]; System.arraycopy(cmd, 0, data, 0, 2); data[2] =
	 * bCypt; data[3] = bMakLen; System.arraycopy(bMak, 0, data, 4, bMakLen);
	 * System.arraycopy(xl, 0, data, 4+bMakLen, 8); System.arraycopy(bMac, 0,
	 * data, 12+bMakLen, 4); System.arraycopy(enlen, 0, data, 16 + bMakLen, 2);
	 * System.arraycopy(bEndata, 0, data, 18 + bMakLen, bEndata.length); return
	 * send(ip, port, timeout, data); }
	 */
	/**
	 * ͨ�ź�����
	 * 
	 * @param ip
	 *            Ӳ�����ܻ�IP��ַ
	 * @param port
	 *            Ӳ�����ܻ��˿�
	 * @param timeout
	 *            ��ʱʱ��
	 * @param data
	 *            ���͵���������
	 * @return
	 */
	private static String send(String ip, String port, String timeout,
			byte[] data) throws Exception {
		Socket socket = null;
		PrintStream ps = null;
		BufferedInputStream bis = null;
		String returndata = "";
		int tout = Integer.parseInt(timeout) * 1000;
		try {
			socket = new Socket();
			InetSocketAddress address = new InetSocketAddress(ip, Integer
					.parseInt(port));
			socket.connect(address, tout);
		} catch (IOException e1) {
			returndata = "4501";
			socket = null;
			e1.printStackTrace();
		}

		if (!returndata.equals("4501")) {
			try {
				socket.setSoTimeout(tout);
				ps = new PrintStream(socket.getOutputStream(), true);
				bis = new BufferedInputStream(socket.getInputStream());
				ps.write(data);
				byte[] tmp = new byte[256];
				int rlen = bis.read(tmp);
				byte[] result = new byte[rlen];
				System.arraycopy(tmp, 0, result, 0, rlen);
				returndata = StringUtil.byte2hex(result);
				if (returndata.startsWith("45")) {
					returndata = "4502";
					throw new Exception("Input(HEX):"
							+ StringUtil.byte2hex(data) + "   Output(HEX):"
							+ returndata);
				}

			} catch (IOException e) {
				returndata = "4501";
				e.printStackTrace();
				throw new Exception("Input(HEX):" + StringUtil.byte2hex(data)
						+ "   Output(HEX):" + returndata);
			} finally {
				try {
					bis.close();
					ps.close();
					socket.close();
				} catch (IOException e) {
					socket = null;
					bis = null;
					ps = null;
				}
			}
		}
		return returndata;
	}
}
