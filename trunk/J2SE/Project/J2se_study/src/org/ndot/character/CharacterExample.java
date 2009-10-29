package org.ndot.character;

import org.junit.Test;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� CharacterExample.java
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
 * ����ʱ��: 2009-10-28
 * 
 */
/*
 * �������ַ���ʱ��Character���е�һЩ�෽���Ǻ����õģ���Щ�����������������ַ����࣬�����ж�һ���ַ��Ƿ��������ַ���ı�һ���ַ���Сд�ȡ�
 * 
 * ��public static boolean isDigit(char ch) ���ch�������ַ���������true�����򷵻�false��
 * 
 * ��public static boolean isLetter(char ch) ���ch����ĸ��������true�����򷵻�false.
 * 
 * �� public static boolean isLetterOrDigit(char
 * ch)���ch�������ַ�����ĸ��������true�����򷵻�false��
 * 
 * ��public static boolean isLowerCase(char ch) ���ch��Сд��ĸ��������true�����򷵻�false��
 * 
 * ��public static boolean isUpperCase(char ch) ���ch�Ǵ�д��ĸ��������true�����򷵻�false��
 * 
 * ��public static char toLowerCase(char ch)����ch��Сд��ʽ��
 * 
 * ��public static char toUpperCase(char ch)����ch�Ĵ�д��ʽ��
 * 
 * ��public static boolean isSpaceChar(char ch)���ch�ǿո񷵻�true��
 * 
 * ������������У����ǽ�һ���ַ����е�Сд��ĸ��ɴ�д��ĸ��������д��ĸ���Сд��ĸ
 */
public class CharacterExample {
	/**
	 * 
	 * <p>
	 * ����: ���� ��Сдת��
	 * <p>
	 * 
	 * @param args
	 *            <p>
	 *            ����:����
	 *            <p>
	 */
	@Test
	public void example1(String args[])

	{

		String s = new String("abcABC123");

		System.out.println(s);

		char a[] = s.toCharArray();

		for (int i = 0; i < a.length; i++)

		{

			if (Character.isLowerCase(a[i]))

			{

				a[i] = Character.toUpperCase(a[i]);

			}

			else if (Character.isUpperCase(a[i]))

			{

				a[i] = Character.toLowerCase(a[i]);

			}

		}

		s = new String(a);

		System.out.println(s);

	}

}
