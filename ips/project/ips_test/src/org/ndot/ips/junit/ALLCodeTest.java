package org.ndot.ips.junit;

import org.junit.Test;
import org.ndot.ips.security.CrypTool;
import org.ndot.ips.util.GenDateTime;

public class ALLCodeTest {
	@Test
	@SuppressWarnings("unused")
	public void testGenDateTime() {
		try {
			String dateTime = GenDateTime.getDateTime();
			String yyyy = dateTime.substring(0, 4);
			String mm = dateTime.substring(4, 6);
			String dd = dateTime.substring(6, 8);
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSecurity() {
		String macbuff = "0200 18622991102000091526 300000 075442 137609 6050 00 041441 0814411200 137363 13726";
		String mk = "f90c5e3a71bf4d6a08a3ed244d102ac5167be398c076ff69";
		String mackey = "C38B2BA1CEC80616";
		String flag = "ASC";
		String keyArith = "DES";
		String macArith = "DES";
		try {
			String mac = CrypTool.getMac(macbuff, mk, mackey, flag, keyArith,
					macArith);
			System.out.println(mac);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void simple() {
		
	}
}
