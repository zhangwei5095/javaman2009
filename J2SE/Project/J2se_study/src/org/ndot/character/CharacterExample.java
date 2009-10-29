package org.ndot.character;

import org.junit.Test;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： CharacterExample.java
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
 * 创建时间: 2009-10-28
 * 
 */
/*
 * 当处理字符串时，Character类中的一些类方法是很有用的，这些方法可以用来进行字符分类，比如判断一个字符是否是数字字符或改变一个字符大小写等。
 * 
 * ·public static boolean isDigit(char ch) 如果ch是数字字符方法返回true，否则返回false。
 * 
 * ·public static boolean isLetter(char ch) 如果ch是字母方法返回true，否则返回false.
 * 
 * · public static boolean isLetterOrDigit(char
 * ch)如果ch是数字字符或字母方法返回true，否则返回false。
 * 
 * ·public static boolean isLowerCase(char ch) 如果ch是小写字母方法返回true，否则返回false。
 * 
 * ·public static boolean isUpperCase(char ch) 如果ch是大写字母方法返回true，否则返回false。
 * 
 * ·public static char toLowerCase(char ch)返回ch的小写形式。
 * 
 * ·public static char toUpperCase(char ch)返回ch的大写形式。
 * 
 * ·public static boolean isSpaceChar(char ch)如果ch是空格返回true。
 * 
 * 在下面的例子中，我们将一个字符串中的小写字母变成大写字母，并将大写字母变成小写字母
 */
public class CharacterExample {
	/**
	 * 
	 * <p>
	 * 功能: 测试 大小写转化
	 * <p>
	 * 
	 * @param args
	 *            <p>
	 *            作者:孙金城
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
