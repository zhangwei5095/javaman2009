package org.ndot.nio.example;

import org.junit.Test;

public class Tester {
	/**
	 * ���ܣ�����  Nonblocking/Single-threaded Server 
	 * ������
	 *     1.��c���� ���� N1.txt
	 *     2.���и� ���Գ���testN1()
	 *     3.��IE�����룺http://localhost:8000/N1.txt
	 *     4.�����ʾ N1.txt�е����ݣ�֤����������
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
