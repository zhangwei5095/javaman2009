package org.ndot.code;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� AscBcd.java
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
 * ����ʱ��: 2009-10-22
 * 
 */
public class AscBcd {
	/**
	 * @����: ������
	 */
	public AscBcd() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sTestData = "1234567890";
		System.out.println("ԭ����:" + sTestData);
		System.out.println("Bcd:" + str2Bcd(sTestData));
		System.out.println("ASC:" + bcd2Str(str2Bcd(sTestData)));

		sTestData = "B510610E627D9E5A";
		System.out.println("ԭ����:" + sTestData);
		System.out.println("Bcd:" + str2Bcd(sTestData));
		System.out.println("ASC:" + bcd2Str(str2Bcd(sTestData)));
	}

	/**
	 * @��������: BCD��תΪ10���ƴ�(����������)
	 * @�������: BCD��
	 * @������: 10���ƴ�
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}

	/**
	 * @��������: 10���ƴ�תΪBCD��
	 * @�������: 10���ƴ�
	 * @������: BCD��
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

}
