package com.nasoft.securty.test;

import com.nasoft.securty.CrypTool;

public  class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String macbuff="0200 18622991102000091526 300000 075442 137609 6050 00 041441 0814411200 137363 13726";
		String mk = "f90c5e3a71bf4d6a08a3ed244d102ac5167be398c076ff69";
		String mackey = "C38B2BA1CEC80616";
		String flag="ASC";
		String keyArith="DES";
		String macArith="DES";
		try {
			String mac = CrypTool.getMac(macbuff, mk, mackey, flag, keyArith, macArith);
			System.out.println(mac);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
