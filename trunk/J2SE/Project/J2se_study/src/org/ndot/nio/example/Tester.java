package org.ndot.nio.example;

import org.junit.Test;

public class Tester {
	/**
	 * 功能：测试  Nonblocking/Single-threaded Server 
	 * 方法：
	 *     1.在c：盘 创建 N1.txt
	 *     2.运行该 测试程序testN1()
	 *     3.在IE中输入：http://localhost:8000/N1.txt
	 *     4.如果显示 N1.txt中的内容，证明运行正常
	 */
	@Test
	public void testN1() {
		String[] args = new String[] { "N1" };
		try {
			Server.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
