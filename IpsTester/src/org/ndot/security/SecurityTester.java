package org.ndot.security;

import org.junit.Test;

import com.nasoft.securty.CrypTool;

public class SecurityTester {
	/**
	 * ���� ����mac
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
			// ʮ�����Ʊ�ʾ��ԭʼ����pin
			String oriPinValue = "9DC48F2DF039C54B";
			// �ʺ�
			String accountNo = "6210210052100000104";
			// ԭ��������Կ
			String oriMk = "1234567890ABCDEF1234567890ABCDEF";
			// ԭ����pin��Կ
			String oriPink = "E31CEE4F2CBF63D8";
			// ԭ�Ƿ�����˺ű�־��1��ʾ�����˺ţ�0��ʾ�������ʺ�
			String oriAcctFlag = "1";
			// ԭpin�����㷨��1��ʾDES��2��ʾ2DES��3��ʾ3DES
			String oriPinArith = "DES";
			// ԭpinKey�����㷨��DES-DES�㷨
			String oriMArith = "DES";
			// Ŀ����������Կ
			String destMkey = "1234567890ABCDEF1234567890ABCDEF";
			// Ŀ������pin��Կ
			String destPinKey = "B8E929DB99FE88C0";
			// Ŀ���Ƿ�����˺ű�־��1��ʾ�����˺ţ�0��ʾ�������ʺ�
			String destAcctFlag = "1";
			// Ŀ��pin�����㷨��1��ʾDES��2��ʾ2DES��3��ʾ3DES
			String destPinArith = "DES";
			// Ŀ��pinKey�����㷨��DES-DES�㷨
			String destMArith = "DES";
			String ip = "";
			String port = "";
			String timeout = "";
			// ԭ���ܱ�־��0����ܣ�1Ӳ����
			String oriEncFlag = "0";
			// Ŀ����ܱ�־��0����ܣ�1Ӳ����
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
