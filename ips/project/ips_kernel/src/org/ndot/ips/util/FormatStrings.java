package org.ndot.ips.util;

public class FormatStrings {
	public static String genMacBuffer(String... strings) {
		StringBuffer macBuffer = new StringBuffer();
		for (int i = 0; i < strings.length; i++) {

		}
		return macBuffer.toString();
	}

	public static String genMacBuffer(String BF1, String BF2, String BF3,
			String BF4, String BF11, String BF12, String BF13, String BF39,
			String BF102, String BF103) {
		String macBuff = BF1.trim();

		if (BF2.trim().length() > 0) {
			String bf2len = Integer.toString(BF2.trim().length());
			if (bf2len.length() == 1) {
				bf2len = "0" + bf2len;
			}
			macBuff = macBuff + " " + bf2len + BF2.trim();
		}

		if (BF3.trim().length() > 0) {
			macBuff = macBuff + " " + BF3.trim();
		}
		if (BF4.trim().length() > 0) {
			macBuff = macBuff + " " + BF4.trim();
		}
		if (BF11.trim().length() > 0) {
			macBuff = macBuff + " " + BF11.trim();
		}
		if (BF12.trim().length() > 0) {
			macBuff = macBuff + " " + BF12.trim();
		}
		if (BF13.trim().length() > 0) {
			macBuff = macBuff + " " + BF13.trim();
		}
		if (BF39.trim().length() > 0) {
			macBuff = macBuff + " " + BF39.trim();
		}
		if (BF102.trim().length() > 0) {
			String bf102len = Integer.toString(BF102.trim().length());
			if (bf102len.length() == 1) {
				bf102len = "0" + bf102len;
			}
			macBuff = macBuff + " " + bf102len + BF102.trim();
		}
		if (BF103.trim().length() > 0) {
			String bf103len = Integer.toString(BF103.trim().length());
			if (bf103len.length() == 1) {
				bf103len = "0" + bf103len;
			}
			macBuff = macBuff + " " + bf103len + BF103.trim();
		}

		return macBuff;
	}
}
