package org.ndot.character;

import java.util.ArrayList;
import java.util.List;

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
 * 文件名： CharacterExample2.java
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
public class CharacterExample2 {

	/*
	 * 引：
	 * 
	 * 本系列提示来自于JAVA开发者联盟（http://developer.java.sun.com）,提供JAVA开发过程中的一些小技巧。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 提示一：使用java.lang.Character类
	 * 
	 * Java.lang.Character是简单数据类型char的包装类。像其它包装类一样，如Integer、Double等等，
	 * 它用来以对象的形式表现简单数据类型值
	 * ，所以那些只能引用对象的方法可以通过它来操作char值。Character类还有一组方法和常量来处理Unicode字符。
	 * 
	 * 本文主要向您介绍使用该类的几种常用方式。例程一演示了如何使用包装类：
	 * 
	 * //引入需要的包
	 */
	@Test
	public void test1()

	{

		// 定义一个链表

		List list = new ArrayList();

		// 向链表中加入元素，必须以对象的形式添加

		list.add(new Character('a'));

		list.add(new Character('b'));

		list.add(new Character('c'));

		// 循环显示链表中的数据

		for (int i = 0; i < list.size(); i++)

		{

			System.out.println(list.get(i));

		}

	}

	/*
	 * 在示例代码中，加入链表中的三个Character对象分别存储了三个字符a，b，c。然后程序循环显示链表中的内容。
	 * 
	 * Character类包含许多“isX”方法，像“isDigit”、“isDefined”等等。您可能认为这些方法并不是必需的，
	 * 如果你想测试一个字符是否是数字，使用下面的简单语句同样可以实现这样的功能。
	 * 
	 * if (c >= '0' && c <= '9')
	 * 
	 * ...
	 * 
	 * 上面的代码在一些场合下确实可以正常运行，并得到你想要的结果，但它却存在一个很大的问题。JAVA使用Unicode字符集，而不是ASCII字符集，
	 * 所以在计数时会出现差错。请试着运行下面的代码，示例二：
	 */
	@Test
	public void test2()

	{

		// 用于记录Unicode字符集中数字字符的个数

		int dig_count = 0;

		// 用于记录Unicode字符集中有意义的字符的个数

		int def_count = 0;

		for (int i = 0; i <= 0xffff; i++)

		{

			// 判断一个字符是否是阿拉伯数字

			if (Character.isDigit((char) i))

			{

				dig_count++;

			}

			// 判断一个字符在Unicode字符集中是否有意义

			if (Character.isDefined((char) i))

			{

				def_count++;

			}

		}

		System.out.println("number of digits = " + dig_count);

		System.out.println("number of defined = " + def_count);

	}

	/*
	 * 
	 * 计算结果表明Unicode字符集中共有208个字符被归类为数字。
	 * 
	 * 这个例子还说明了另外一点：Unicode字符集中，并不是所有的Unicode字符都有实际的意义。计算结果表明，
	 * 在65536个字符中只有59177个字符有着实际的意义。（即使用Character.isDefined方法测试，返回值为TRUE）
	 * 
	 * Character类另外一个大显身手的地方就是在字符的大小写转化中，让我们先来看一下下面的示例代码三：
	 */
	@Test
	public void test3()

	{

		char cupper = 'A';

		char clower;

		// 使用ASCII方法将大写字符转换成小写

		clower = (char) (cupper + 0x20);

		System.out.println("convert to lower case using the ASCII convention");

		System.out.println("cupper #1 = " + cupper);

		System.out.println("clower #1 = " + clower);

		System.out.println();

		// 使用Character.toLowerCase()方法将大写字符转换成小写

		System.out
				.println("convert to lower case using Character.toLowerCase()");

		clower = Character.toLowerCase(cupper);

		System.out.println("cupper #2 = " + cupper);

		System.out.println("clower #2 = " + clower);

	}

	/*
	 * 
	 * 如果你使用的是ASCII码字符集，通常是大写字母加上ASCII码0x20（十进制32）转换成小写字母。示例程序的第一部分正是使用的这种方法，但在使用Unicode字符集时
	 * ，则会出现错误。主要的原因在于，Unicode字符集中大小写字母并不能保证刚好差0x20。所以在这种情况下，
	 * 使用Character类提供的toLowerCase方法是更好的选择。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Character类也包含了一些方法用于字符与整形值之间的转换。例如，在Integer.parseInt中用于将指定基数的字符串转换成相应的整型值，
	 * 如下面的语句所示：
	 * 
	 * Integer.parseInt("-ff", 16) == -255
	 * 
	 * 下面的示例代码四，演示了这些方法的应用：
	 */
	@Test
	public void test4()

	{

		// 返回字符z在指定基数为36时，对应的数字值

		int dig = Character.digit('z', 36);

		System.out.println(dig);

		// 把 16 进制的字符转为10进制
		int x = Character.digit('c', 16);
		System.out.println("x=" + x);// x=12 c->99 a-> 97 c-a+10=12
		// 上面的两句执行过程是将c的十进制减去a的十进制然后加上10。

		// 返回数字在指定基数为36时，对应的字符值

		char cdig = Character.forDigit(dig, 36);

		System.out.println(cdig);

		// 返回Unicode字符\u217c对应的数字值

		int rn50 = Character.getNumericValue('\u217c');

		System.out.println(rn50);

	}

	/*
	 * 
	 * Character.digit方法返回在指定基数的前提下，字符对应的整型值。所以如示例所示，在基数为36时，数字只包括0-9和字母a-z，所以字母“
	 * z”对应的数字值为35。
	 * 
	 * Character.forDigit方法则做相反的操作；在基数为36时，值为35的数字对应的字母为“z”。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Character.getNumericValue返回一个字符对应的数字值，该值的对应关系描述是在Unicode属性表中。示例所示，Unicode值
	 * \U217C对应的罗马数字为“L”，它对应的数字值就是50。
	 * 
	 * Unicode属性表也用来指定Unicode字符所属的类型。类型就是种类范畴，例如标点符号、货币符号、字母等等。
	 * 下面的示例程序五显示了Unicode字符集中货币符号所对应的十六进制值：
	 */
	@Test
	public void test5()

	{

		int j = 0;

		System.out.println("No " + "DecNumber " + "HexNumber " + "CharValue");

		for (int i = 0; i <= 0xffff; i++)

		{

			// 如果取得的字符的类型与常量CRUUENCY_SYMBOL相等

			if (Character.getType((char) i) == Character.CURRENCY_SYMBOL)

			{

				++j;

				// 打印出字符所对应的十六进制值

				System.out.println(j + " " + i + " " + Integer.toHexString(i)
						+ " " + (char) i);

			}

		}

	}

	/*
	 * 
	 * Unicode字符集中共有27个货币类型符号，但实际上并非所有这些都具有实际意义。第一个货币符号对应的十六进制值是，0x24，对应的符号为“$”。
	 * 
	 * 最后是使用Character类来操纵Unicode字符块（这是在Unicode2.0中定义的）。所谓Unicode字符块，
	 * 就是一组相近的Unicode字符的集合。例如，拉丁语字符、阿拉伯语字符、箭头符号等等。下面的示例程序六就打印出了希腊字符块中包含的所有字符的值：
	 */
	@Test
	public void test6()

	{

		int j = 0;

		System.out.println("No " + "DecNumber " + "HexNumber " + "CharValue");

		for (int i = 0; i <= 0xffff; i++)

		{

			// 如果字符属于希腊字符集

			if (Character.UnicodeBlock.of((char) i) == Character.UnicodeBlock.GREEK)

			{

				++j;

				// 打印出对应的十六进制值

				System.out.println(j + " " + i + " " + Integer.toHexString(i)
						+ " " + (char) i);

			}

		}

	}

	/*
	 * Java 中 Character 的 digit 与 forDigit 是这样申明的。
	 * 
	 * public static int digit(char ch, int radix) public static char
	 * forDigit(int digit, int radix)
	 * 
	 * 其中有一个参数是非曲直 radix 。字面上的意思是基数，看了 JDK 的CHM
	 * 也没有明白这个基数是什么意思。测试之后，发现这个基数可以理解成进制。二进制就是 2 ，八进制就是8，十六进制就是16。 radix 最小值为
	 * MIN_RADIX (2) ，最大值为 MAX_RADIX (36)。 数值是用 0~9,A~Z 来表示。字符大小写是一样的。
	 */
	@Test
	public void test7() {
		char n = '5';
		System.out.println(n);

		System.out.println(Character.getNumericValue(n));

		System.out.println(Character.MIN_RADIX);
		System.out.println(Character.MAX_RADIX);
		n = Character.forDigit(6, 10);
		System.out.println(n);

		n = 'B';
		System.out.println(Character.digit(n, 16));

	}

	/*
	 * static int digit(char ch, int radix)
	 * ：根据基数返回当前字符的值的十进制。如果不满足Character.MIN_RADIX <= radix <=
	 * Character.MAX_RADIX，或者，ch不是radix基数中的有效值，返回"-1"；如果ch是“大写”的A到Z之间，则返回ch -
	 * 'A' + 10 的值；如果是“小写”a到z之间，返回ch - 'a' + 10 的值。
	 */
	@Test
	public void test8() {
		System.out.println("Character.MIN_RADIX: " + Character.MIN_RADIX);
		System.out.println("Character.MAX_RADIX: " + Character.MAX_RADIX);
		System.out
				.println("Character.digit('2',2): " + Character.digit('2', 2));
		System.out.println("Character.digit('7',10): "
				+ Character.digit('7', 10));
		System.out
				.println("Character.digit('7',8): " + Character.digit('7', 8));
		System.out.println("Character.digit('b',16): "
				+ Character.digit('b', 16));
		System.out.println("Character.digit('b',36): "
				+ Character.digit('b', 36));
		System.out
				.println("Character.digit('c',8): " + Character.digit('c', 8));
		System.out.println("Character.digit('g',36): "
				+ Character.digit('g', 36));
		System.out.println("Character.digit('z',36): "
				+ Character.digit('z', 36));
		/*
		 * Character.MIN_RADIX: 2 Character.MAX_RADIX: 36
		 * Character.digit('2',2): -1 Character.digit('7',10): 7
		 * Character.digit('7',8): 7
		 */

	}

	/*
	 * static char forDigit(int digit, int radix)
	 * ：根据特定基数判断当前数值表示的字符。4的逆运算，非法数值时返回“'\u0000'”。-
	 */
	@Test
	public void test9() {
		System.out.println("Character.MIN_RADIX: " + Character.MIN_RADIX);
		System.out.println("Character.MAX_RADIX: " + Character.MAX_RADIX);
		System.out.println("Character.forDigit(2,2): "
				+ Character.forDigit(2, 2));
		System.out.println("Character.forDigit(7,10): "
				+ Character.forDigit(7, 10));
		System.out.println("Character.forDigit(15,16): "
				+ Character.forDigit(15, 16));
	}
}
