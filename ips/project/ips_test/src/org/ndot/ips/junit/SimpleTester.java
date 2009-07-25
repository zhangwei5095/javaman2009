package org.ndot.ips.junit;

import org.junit.Test;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_test
 * 
 * 文件名： SimpleTester.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-7-4
 * 
 */
public class SimpleTester {
	@Test
	public void TestSubString() {
		try {
			String bf54 = "00000920000000000000000000000500000000000000000000000000000";
			String bf54_1_1 = bf54.substring(1, 1);
			String bf54_1_13 = bf54.substring(1, 13);
			String bf54_14_12 = bf54.substring(14, 14 + 12);
			String bf54_26_12 = bf54.substring(26, 26 + 12);
			String value = bf54_1_13 + bf54_1_1 + bf54_14_12 + bf54_1_1
					+ bf54_26_12;
			String t = bf54.substring(0, 40);
			System.out.println("t=" + t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
