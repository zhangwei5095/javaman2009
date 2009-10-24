package org.ndot.code;

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
 * 文件名： AscBcd2.java
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
 * 创建时间: 2009-10-22
 * 
 */
public class AscBcd2 {
	/**
	 * @功能: 构造体
	 */
	public AscBcd2() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**/
		String sTestData = "01001000154666210210331700000593   00000000000000040745920120401000000000000100D121030121000884013    02000156956210210331700000593   00000000000000040885920120401000000000001400D121030121000884013    03000157346210210331700000593   00000000000000040895920120401000000000000100D121030121000884013    04000157596210210331700000593   00000000000000040915920120401000000000000200D121030121000884013    05000159626210210331700000593   00000000000000041415920120401000000000005000D121030121000884013    06000159726210210331700000593   00000000000000091415920120401000000000500000D121030121000884013    07000159836210210331700000593   00000000000000041165920120401000000000502500C121030121000884013    08000166066210210331700000593   00000000000000041145920120401000000000000200C121030121000884013    09000168096210210331700000593   00000000000000041155920120401000000000000100D121030121000884013    10000168456210210331700000593   00000000000000041305920120401000000000001500D121030121000884013    ";
//		sTestData = "11    4   5 1";
		byte bBCD[] = new byte[(sTestData.length() + 1) / 2];

		bBCD = str2Bcd(sTestData);
		String hexBcd = ISOUtil.byte2HexStr(bBCD, bBCD.length);
		System.out.println("hexBcd is :" + hexBcd);
		// bBCD=str2cbcd(sTestData,true,bBCD,0);
		String str = bcd2Str(bBCD);
		// String str=cbcd2str(bBCD,0,bBCD.length*2,true);
		System.out.println("原数据:" + sTestData);
		System.out.println("结     果:" + str);

		/*
		 * System.out.println("========================================");
		 * 
		 * sTestData = "= ABCDEF0123     456789abcdef"; bBCD =
		 * str2Bcd(sTestData); str = bcd2Str(bBCD); System.out.println("原数据:" +
		 * sTestData); System.out.println("Bcd:" + bBCD);
		 * System.out.println("ASC:" + str);
		 */

		/*
		 * try { Stringhex=
		 * "3030303030303232313134353333393030303030323130e23a44980b80ac00010000080000002131393632313032313030353231303030303031303431303032303031313231313435333339313435383139323030393130323132303132303430313135323030323131343030303030303030303030303030303030303031353437342020202030303038bad3b1b1c5a9d0c531303030303032313135363132313033313030303030303030303030303030303020202020202020202020202020202020202020202020202020202020202020202020202020202030303031303030099300100100014983621021005210000010400000000000000001041207020120401000000000000100d22103012100000270700000200015223621021005210000010400000000000000001041187020120401000000000000200c22103012100000270700000300015258621021005210000010400000000000000001041157020120401000000000000300c22103012100000270700000400015260621021005210000010400000000000000001041037020120401000000000001200c22103012100000270700000500015260621021005210000010400000000000000001041137020120401000000000001000d22103012100000270700000600015267621021005210000010400000000000000001041107020120401000000000000300c22103012100000270700000700015269621021005210000010400000000000000001041117020120401000000000000100d22103012100000270700000800015271621021005210000010400000000000000001041137020120401000000000000200d22103012100000270700000900015280621021005210000010400000000000000001041107020120401000000000000300c22103012100000270700001000015466621021005210000010400000000000000001041087020120401000000000000200c22103012100000270700000f0c14a82fe3e56d"
		 * ; byte[] bcd=ISOUtil.hex2byte(hex); String rs =
		 * cbcd2str(bcd,228,993,true); System.out.println(rs); } catch
		 * (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输出结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {

		// 原数据的长度
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = " " + asc;
			len = asc.length();
		}

		// 原数据
		byte bOriginalData[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		// 将字符串数据转换成字节数据
		bOriginalData = asc.getBytes();

		// 转换后的BCD码
		byte bBCD[] = new byte[len];

		int sH, sL;

		for (int p = 0; p < asc.length() / 2; p++) {

			if ((bOriginalData[2 * p] >= 'a') && (bOriginalData[2 * p] <= 'f')) {
				sH = bOriginalData[2 * p] - 'a' + 10;
			} else if ((bOriginalData[2 * p] >= 'A')
					&& (bOriginalData[2 * p] <= 'F')) {
				sH = bOriginalData[2 * p] - 'A' + 10;
			} else {
				sH = bOriginalData[2 * p] & 0x0f;
			}

			if ((bOriginalData[2 * p + 1] >= 'a')
					&& (bOriginalData[2 * p + 1] <= 'f')) {
				sL = bOriginalData[2 * p + 1] - 'a' + 10;
			} else if ((bOriginalData[2 * p + 1] >= 'A')
					&& (bOriginalData[2 * p + 1] <= 'F')) {
				sL = bOriginalData[2 * p + 1] - 'A' + 10;
			} else {
				sL = bOriginalData[2 * p + 1] & 0x0f;
			}

			bBCD[p] = (byte) ((sH << 4) + sL);
		}
		return bBCD;
	}

	/**
	 * @函数功能: BCD码串转化为字符串
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		char temp[] = new char[bytes.length * 2], val;

		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}

	/**
	 * 
	 * <p>
	 * 功能:字符串转为BCD码
	 * 
	 * <p>
	 * 
	 * @param asc
	 *            字符串
	 * @return BCD码 作者:孙金城
	 *         <p>
	 */
	public static byte[] str2cbcd(String asc, boolean padLeft, byte[] bcd,
			int offset) {
		// 原数据的长度
		asc = ((asc.length() % 2) > 0 && padLeft) ? "0" + asc : asc;
		// 原数据
		byte bOriginalData[] = new byte[asc.length()];
		// 将字符串数据转换成字节数据
		bOriginalData = asc.getBytes();
		int sH, sL;
		for (int p = 0; p < asc.length() / 2; p++) {

			if ((bOriginalData[2 * p] >= 'a') && (bOriginalData[2 * p] <= 'f')) {
				sH = bOriginalData[2 * p] - 'a' + 10;
			} else if ((bOriginalData[2 * p] >= 'A')
					&& (bOriginalData[2 * p] <= 'F')) {
				sH = bOriginalData[2 * p] - 'A' + 10;
			} else {
				sH = bOriginalData[2 * p] & 0x0f;
			}

			if ((bOriginalData[2 * p + 1] >= 'a')
					&& (bOriginalData[2 * p + 1] <= 'f')) {
				sL = bOriginalData[2 * p + 1] - 'a' + 10;
			} else if ((bOriginalData[2 * p + 1] >= 'A')
					&& (bOriginalData[2 * p + 1] <= 'F')) {
				sL = bOriginalData[2 * p + 1] - 'A' + 10;
			} else {
				sL = bOriginalData[2 * p + 1] & 0x0f;
			}
			bcd[p] = (byte) ((sH << 4) + sL);
		}
		return bcd;
	}

	/**
	 * 
	 * <p>
	 * 功能: BCD码串转化为字符串
	 * <p>
	 * 
	 * @param bytes
	 *            BCD码
	 * @return ASC 串 作者:孙金城
	 *         <p>
	 */
	public static String cbcd2str(byte[] bytes, int offset, int len,
			boolean leftpad) {
		char temp[] = new char[len + 1], val;
		int end = (len + 1) / 2 + offset;

		for (int i = offset; i < end; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[(i - offset) * 2] = (char) (val > 9 ? val + 'A' - 10
					: val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[(i - offset) * 2 + 1] = (char) (val > 9 ? val + 'A' - 10
					: val + '0');
		}
		return new String(temp);
	}
}
