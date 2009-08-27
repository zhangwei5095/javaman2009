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
	 * 根据MacBuff计算Mac
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param mak
	 *            mac密钥密文(H)
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
	 *            硬件加密机的IP(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param pik1
	 *            原密文pin密钥(H)
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
	 * 使用MAC密文（次主密钥加密的），计算MAC
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
	 * 重置密钥时候使用
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param mak
	 *            mac密钥密文(H)
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
	 * 加密或解密PIN
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param pinkey
	 *            PIN密钥,密文(H)
	 * @param data
	 *            要加密/解密的数据(H)
	 * @param flag
	 *            加解密标志(1,加密；0，解密)
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
	 * 将次主密钥转换为用主密钥加密
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param mmklen
	 *            次主密钥长度(A)
	 * @param index
	 *            次主密钥索引号(A)
	 * @param keytype
	 *            密钥类型 (01：通信主密钥;11：PIN加密密钥;12：MAC计算密钥; 13：数据加密密钥;
	 *            21:CVV计算密钥;22: PVV计算密钥) *
	 * @param key
	 *            密钥,密文
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
	 * 用主密钥加密明文的密码
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param keytype
	 *            密钥类型(A) (01：通信主密钥;11：PIN加密密钥;12：MAC计算密钥; 13：数据加密密钥;
	 *            21:CVV计算密钥;22: PVV计算密钥)
	 * @param key
	 *            明文密钥(H)
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
	 * 保存区域主密钥
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param index
	 *            次主密钥索引号(A)
	 * @param key
	 *            次主密钥明文(H)
	 * @return 1保存成功，0保存失败
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
	 * 将用区域主密钥加密的密钥转换为用本地主密钥加密。
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            加密机端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param wklen
	 *            通信主密钥长度(A)
	 * @param index
	 *            通信主密钥索引(A)
	 * @param keytype
	 *            工作密钥类型(H) (01：通信主密钥;11：PIN加密密钥;12：MAC计算密钥; 13：数据加密密钥;
	 *            21:CVV计算密钥;22: PVV计算密钥)
	 * @param wk
	 *            通信主密钥加密的工作密钥(H)
	 * @return
	 */
	private static String sendCmdD102(String ip, String port, String timeout,
			String wklen, String index, String keytype, String wk)
			throws Exception {

		byte[] cmd = StringUtil.hex2Byte("D102"); // 命令数据
		byte[] bWk = StringUtil.hex2Byte(wk); // 工作密钥，密文
		byte bWkeyLen = (byte) bWk.length; // 工作密钥长度
		byte bLkLen = Byte.parseByte(wklen); // 通信密钥长度
		byte[] bIndex = StringUtil.short2byte(Integer.parseInt(index)); // 通信主密钥索引
		byte bType = StringUtil.hex2Byte(keytype)[0]; // 密钥类型

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
	 * 将用本地主密钥加密的密钥转换为用区域主密钥加密
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
	 * StringUtil.hex2Byte("D104"); //命令数据 byte[] bWk = StringUtil.hex2Byte(wk);
	 * //工作密钥，密文 byte bWkeyLen = (byte)bWk.length; //工作密钥长度 byte bLkLen =
	 * Byte.parseByte(wklen); //通信密钥长度 byte[] bIndex =
	 * StringUtil.short2byte(Integer.parseInt(index)); //通信主密钥索引 byte bType =
	 * StringUtil.hex2Byte(keytype)[0]; //密钥类型
	 * 
	 * byte[] data = new byte[7+bWkeyLen]; System.arraycopy(cmd, 0, data, 0, 2);
	 * data[2] = bWkeyLen; data[3] = bLkLen; System.arraycopy(bIndex, 0, data,
	 * 4, 2); data[6] = bType; System.arraycopy(bWk, 0, data, 7, bWkeyLen);
	 * 
	 * return send(ip, port, timeout, data); }
	 */
	/**
	 * 将用本地主密钥加密的密钥转换为用区域主密钥加密。
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            加密机端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param wklen
	 *            通信密钥长度(A)
	 * @param index
	 *            通信主密钥索引(A)
	 * @param keytype
	 *            工作密钥类型(H) (01：通信主密钥;11：PIN加密密钥;12：MAC计算密钥; 13：数据加密密钥;
	 *            21:CVV计算密钥;22: PVV计算密钥)
	 * @param wk
	 *            通信主密钥加密的工作密钥(H)
	 * @return
	 */
	/*
	 * public static String sendCmdD104(String ip, String port, String timeout,
	 * String wklen, String index, String keytype, String wk) {
	 * 
	 * byte[] cmd = StringUtil.hex2Byte("D104"); //命令数据 byte[] bWk =
	 * StringUtil.hex2Byte(wk); //工作密钥，密文 byte bWkeyLen = (byte)bWk.length;
	 * //工作密钥长度 byte bLkLen = Byte.parseByte(wklen); //通信密钥长度 byte[] bIndex =
	 * StringUtil.short2byte(Integer.parseInt(index)); //通信主密钥索引 byte bType =
	 * StringUtil.hex2Byte(keytype)[0]; //秘要类型
	 * 
	 * byte[] data = new byte[7+bWkeyLen]; System.arraycopy(cmd, 0, data, 0, 2);
	 * data[2] = bWkeyLen; data[3] = bLkLen; System.arraycopy(bIndex, 0, data,
	 * 4, 2); data[6] = bType; System.arraycopy(bWk, 0, data, 7, bWkeyLen);
	 * 
	 * return send(ip, port, timeout, data); }
	 */
	/**
	 * 输入指定长度的明文密钥，并用加密机本地主密钥加密后返回其密文密钥和校验码
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param type
	 *            密钥类型(H) (01：通信主密钥;11：PIN加密密钥;12：MAC计算密钥; 13：数据加密密钥;
	 *            21:CVV计算密钥;22: PVV计算密钥)
	 * @param key
	 *            密钥密文(H)
	 * @return
	 */
	private static String sendCmdD108(String ip, String port, String timeout,
			String type, String key) throws Exception {
		byte[] keyByte = StringUtil.hex2Byte(key);
		byte len = (byte) keyByte.length;
		byte[] data = new byte[4 + len];
		byte[] cmd = StringUtil.hex2Byte("D108");
		System.arraycopy(cmd, 0, data, 0, 2); // 命令数据
		data[2] = len;
		data[3] = StringUtil.hex2Byte(type)[0];

		System.arraycopy(keyByte, 0, data, 4, len);
		return send(ip, port, timeout, data);
	}

	/**
	 * 用分量合成MMK
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
	 * 导出区域主密钥,并用LMK加密。
	 * 
	 * @param ip
	 *            硬件加密机的IP(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param index
	 *            区域主密钥索引号(H)
	 * @param len
	 *            密钥长度(A)
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
	 * 存储区域主密钥
	 * 
	 * @param ip
	 *            硬件加密机IP(A)
	 * @param port
	 *            硬件加密机开放端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param index
	 *            区域主密钥的索引号(H)
	 * @param cklen
	 *            校验值长度
	 * @param key
	 *            被主密钥加密的区域主密钥(H)
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
	 * 用输入密钥对数据进行加密
	 * 
	 * @param ip
	 *            硬件加密机的IP地址(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param cypt
	 *            加密方式(H)
	 *            (11：DES_ECB;21：DES_CBC;12：3DES_ECB(双长度);22：3DES_ECB（双长度）
	 *            13：3DES_ECB（3长度）;23：3DES_ECB（3长度）)
	 * @param key
	 *            LMK加密的密钥(H)
	 * @param encyptdata
	 *            需要加密的数据(H)
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
	 * 用输入密钥对数据进行解密
	 * 
	 * @param ip
	 *            硬件加密机的IP地址(A)
	 * @param port
	 *            硬件加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param cypt
	 *            加密方式(H)
	 *            (11：DES_ECB;21：DES_CBC;12：3DES_ECB(双长度);22：3DES_ECB（双长度）
	 *            13：3DES_ECB（3长度）;23：3DES_ECB（3长度）)
	 * @param key
	 *            LMK加密的密钥(H)
	 * @param encyptdata
	 *            需要解密的数据(H)
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
	 * 用输入的MAK对输入数据计算MAC
	 * 
	 * @param ip
	 *            硬件加密机的IP地址(A)
	 * @param port
	 *            加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param mak
	 *            MAK密文(H)
	 * @param endata
	 *            数据(H)
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
	 * 对8个0x00进行mac计算，用于重置密钥
	 * 
	 * @param ip
	 *            硬件加密机的IP地址(A)
	 * @param port
	 *            加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param mak
	 *            MAK密文(H)
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
	 * 校验 MAC
	 * 
	 * @param ip
	 *            加密机的IP地址(A)
	 * @param port
	 *            加密机的端口(A)
	 * @param timeout
	 *            超时时间(A)
	 * @param mak
	 *            MAK密钥，密文(H)
	 * @param mac
	 *            待检验的mac长度
	 * @param endata
	 *            macbuff数据
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
	 * 通信函数，
	 * 
	 * @param ip
	 *            硬件加密机IP地址
	 * @param port
	 *            硬件加密机端口
	 * @param timeout
	 *            超时时间
	 * @param data
	 *            发送的命令数据
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
