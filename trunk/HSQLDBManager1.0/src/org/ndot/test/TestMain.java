package org.ndot.test;

public class TestMain {
	public static String[] upperKeyWord(String keyword) {
		int len = keyword.length();
		String[] keywords = new String[len];
		for (int i = 0; i < keywords.length; i++) {
			int nc = keyword.charAt(i) - 32;
			char tem = (char) nc;
			String newkw = keyword.substring(0, i);
			newkw += tem;
			newkw += keyword.substring(i+1);
			keywords[i] = newkw;
		}
		for (int i = 0; i < keywords.length; i++) {
			System.out.println(keywords[i]);
		}
		return keywords;
	}

	public static void main(String[] args) {
		TestMain.upperKeyWord("select");
		// char a=97;
		// System.out.println(a);
	}
}
