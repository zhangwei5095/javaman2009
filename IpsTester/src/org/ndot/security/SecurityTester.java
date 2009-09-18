package org.ndot.security;

import org.junit.Test;

import com.nasoft.securty.CrypTool;

public class SecurityTester {
	/**
	 * 测试 计算mac
	 */
	@Test
	public void getMacTest() {
		try {
			String macbuff = "0200 18622991102000091526 300000 075442 137609 6050 00 041441 0814411200 137363 13726";
			String mk = "f90c5e3a71bf4d6a08a3ed244d102ac5167be398c076ff69";
			String mackey = "C38B2BA1CEC80616";
			String flag = "ASC";
			String keyArith = "DES";
			String macArith = "DES";

			String mac = CrypTool.getMac(macbuff, mk, mackey, flag, keyArith,
					macArith);
			System.out.println(mac);

			mac = CrypTool.getMacReturn8Bytes(macbuff, mk, mackey, flag,
					keyArith, macArith);
			System.out.println(mac);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void pinTransTest() {
		try {
			// 十六进制表示的原始密文pin
			String oriPinValue = "9DC48F2DF039C54B";
			// 帐号
			String accountNo = "6210210052100000104";
			// 原明文主密钥
			String oriMk = "1234567890ABCDEF1234567890ABCDEF";
			// 原密文pin密钥
			String oriPink = "E31CEE4F2CBF63D8";
			// 原是否带主账号标志，1表示带主账号，0表示不带主帐号
			String oriAcctFlag = "1";
			// 原pin加密算法，1表示DES，2表示2DES，3表示3DES
			String oriPinArith = "DES";
			// 原pinKey加密算法，DES-DES算法
			String oriMArith = "DES";
			// 目标明文主密钥
			String destMkey = "1234567890ABCDEF1234567890ABCDEF";
			// 目标密文pin密钥
			String destPinKey = "B8E929DB99FE88C0";
			// 目标是否带主账号标志，1表示带主账号，0表示不带主帐号
			String destAcctFlag = "1";
			// 目标pin加密算法，1表示DES，2表示2DES，3表示3DES
			String destPinArith = "DES";
			// 目标pinKey加密算法，DES-DES算法
			String destMArith = "DES";
			String ip = "";
			String port = "";
			String timeout = "";
			// 原加密标志：0软加密，1硬加密
			String oriEncFlag = "0";
			// 目标加密标志：0软加密，1硬加密
			String destEncFlag = "0";
			String destPin = CrypTool.pinTransfer(oriPinValue, accountNo,
					oriMk, oriPink, oriAcctFlag, oriPinArith, oriMArith,
					destMkey, destPinKey, destAcctFlag, destPinArith,
					destMArith, ip, port, timeout, oriEncFlag, destEncFlag);
			System.out.println("Result: " + destPin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
