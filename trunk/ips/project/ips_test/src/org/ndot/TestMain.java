package org.ndot;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		_DOWHILE: while(true) {
			String a = null;
			try {
				System.out.println("dfd");
				a.length();
			} catch (Exception e) {
				e.printStackTrace();
				continue _DOWHILE;
			}

		}
	}

}
