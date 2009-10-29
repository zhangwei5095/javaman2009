package org.ndot.character;

import java.util.ArrayList;
import java.util.List;

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
 * �ļ����� CharacterExample2.java
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
public class CharacterExample2 {

	/*
	 * ����
	 * 
	 * ��ϵ����ʾ������JAVA���������ˣ�http://developer.java.sun.com��,�ṩJAVA���������е�һЩС���ɡ�
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ��ʾһ��ʹ��java.lang.Character��
	 * 
	 * Java.lang.Character�Ǽ���������char�İ�װ�ࡣ��������װ��һ������Integer��Double�ȵȣ�
	 * �������Զ������ʽ���ּ���������ֵ
	 * ��������Щֻ�����ö���ķ�������ͨ����������charֵ��Character�໹��һ�鷽���ͳ���������Unicode�ַ���
	 * 
	 * ������Ҫ��������ʹ�ø���ļ��ֳ��÷�ʽ������һ��ʾ�����ʹ�ð�װ�ࣺ
	 * 
	 * //������Ҫ�İ�
	 */
	@Test
	public void test1()

	{

		// ����һ������

		List list = new ArrayList();

		// �������м���Ԫ�أ������Զ������ʽ���

		list.add(new Character('a'));

		list.add(new Character('b'));

		list.add(new Character('c'));

		// ѭ����ʾ�����е�����

		for (int i = 0; i < list.size(); i++)

		{

			System.out.println(list.get(i));

		}

	}

	/*
	 * ��ʾ�������У����������е�����Character����ֱ�洢�������ַ�a��b��c��Ȼ�����ѭ����ʾ�����е����ݡ�
	 * 
	 * Character�������ࡰisX����������isDigit������isDefined���ȵȡ���������Ϊ��Щ���������Ǳ���ģ�
	 * ����������һ���ַ��Ƿ������֣�ʹ������ļ����ͬ������ʵ�������Ĺ��ܡ�
	 * 
	 * if (c >= '0' && c <= '9')
	 * 
	 * ...
	 * 
	 * ����Ĵ�����һЩ������ȷʵ�����������У����õ�����Ҫ�Ľ��������ȴ����һ���ܴ�����⡣JAVAʹ��Unicode�ַ�����������ASCII�ַ�����
	 * �����ڼ���ʱ����ֲ����������������Ĵ��룬ʾ������
	 */
	@Test
	public void test2()

	{

		// ���ڼ�¼Unicode�ַ����������ַ��ĸ���

		int dig_count = 0;

		// ���ڼ�¼Unicode�ַ�������������ַ��ĸ���

		int def_count = 0;

		for (int i = 0; i <= 0xffff; i++)

		{

			// �ж�һ���ַ��Ƿ��ǰ���������

			if (Character.isDigit((char) i))

			{

				dig_count++;

			}

			// �ж�һ���ַ���Unicode�ַ������Ƿ�������

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
	 * ����������Unicode�ַ����й���208���ַ�������Ϊ���֡�
	 * 
	 * ������ӻ�˵��������һ�㣺Unicode�ַ����У����������е�Unicode�ַ�����ʵ�ʵ����塣������������
	 * ��65536���ַ���ֻ��59177���ַ�����ʵ�ʵ����塣����ʹ��Character.isDefined�������ԣ�����ֵΪTRUE��
	 * 
	 * Character������һ���������ֵĵط��������ַ��Ĵ�Сдת���У�������������һ�������ʾ����������
	 */
	@Test
	public void test3()

	{

		char cupper = 'A';

		char clower;

		// ʹ��ASCII��������д�ַ�ת����Сд

		clower = (char) (cupper + 0x20);

		System.out.println("convert to lower case using the ASCII convention");

		System.out.println("cupper #1 = " + cupper);

		System.out.println("clower #1 = " + clower);

		System.out.println();

		// ʹ��Character.toLowerCase()��������д�ַ�ת����Сд

		System.out
				.println("convert to lower case using Character.toLowerCase()");

		clower = Character.toLowerCase(cupper);

		System.out.println("cupper #2 = " + cupper);

		System.out.println("clower #2 = " + clower);

	}

	/*
	 * 
	 * �����ʹ�õ���ASCII���ַ�����ͨ���Ǵ�д��ĸ����ASCII��0x20��ʮ����32��ת����Сд��ĸ��ʾ������ĵ�һ��������ʹ�õ����ַ���������ʹ��Unicode�ַ���ʱ
	 * �������ִ�����Ҫ��ԭ�����ڣ�Unicode�ַ����д�Сд��ĸ�����ܱ�֤�պò�0x20����������������£�
	 * ʹ��Character���ṩ��toLowerCase�����Ǹ��õ�ѡ��
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
	 * Character��Ҳ������һЩ���������ַ�������ֵ֮���ת�������磬��Integer.parseInt�����ڽ�ָ���������ַ���ת������Ӧ������ֵ��
	 * ������������ʾ��
	 * 
	 * Integer.parseInt("-ff", 16) == -255
	 * 
	 * �����ʾ�������ģ���ʾ����Щ������Ӧ�ã�
	 */
	@Test
	public void test4()

	{

		// �����ַ�z��ָ������Ϊ36ʱ����Ӧ������ֵ

		int dig = Character.digit('z', 36);

		System.out.println(dig);

		// �� 16 ���Ƶ��ַ�תΪ10����
		int x = Character.digit('c', 16);
		System.out.println("x=" + x);// x=12 c->99 a-> 97 c-a+10=12
		// ���������ִ�й����ǽ�c��ʮ���Ƽ�ȥa��ʮ����Ȼ�����10��

		// ����������ָ������Ϊ36ʱ����Ӧ���ַ�ֵ

		char cdig = Character.forDigit(dig, 36);

		System.out.println(cdig);

		// ����Unicode�ַ�\u217c��Ӧ������ֵ

		int rn50 = Character.getNumericValue('\u217c');

		System.out.println(rn50);

	}

	/*
	 * 
	 * Character.digit����������ָ��������ǰ���£��ַ���Ӧ������ֵ��������ʾ����ʾ���ڻ���Ϊ36ʱ������ֻ����0-9����ĸa-z��������ĸ��
	 * z����Ӧ������ֵΪ35��
	 * 
	 * Character.forDigit���������෴�Ĳ������ڻ���Ϊ36ʱ��ֵΪ35�����ֶ�Ӧ����ĸΪ��z����
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
	 * Character.getNumericValue����һ���ַ���Ӧ������ֵ����ֵ�Ķ�Ӧ��ϵ��������Unicode���Ա��С�ʾ����ʾ��Unicodeֵ
	 * \U217C��Ӧ����������Ϊ��L��������Ӧ������ֵ����50��
	 * 
	 * Unicode���Ա�Ҳ����ָ��Unicode�ַ����������͡����;������෶�룬��������š����ҷ��š���ĸ�ȵȡ�
	 * �����ʾ����������ʾ��Unicode�ַ����л��ҷ�������Ӧ��ʮ������ֵ��
	 */
	@Test
	public void test5()

	{

		int j = 0;

		System.out.println("No " + "DecNumber " + "HexNumber " + "CharValue");

		for (int i = 0; i <= 0xffff; i++)

		{

			// ���ȡ�õ��ַ��������볣��CRUUENCY_SYMBOL���

			if (Character.getType((char) i) == Character.CURRENCY_SYMBOL)

			{

				++j;

				// ��ӡ���ַ�����Ӧ��ʮ������ֵ

				System.out.println(j + " " + i + " " + Integer.toHexString(i)
						+ " " + (char) i);

			}

		}

	}

	/*
	 * 
	 * Unicode�ַ����й���27���������ͷ��ţ���ʵ���ϲ���������Щ������ʵ�����塣��һ�����ҷ��Ŷ�Ӧ��ʮ������ֵ�ǣ�0x24����Ӧ�ķ���Ϊ��$����
	 * 
	 * �����ʹ��Character��������Unicode�ַ��飨������Unicode2.0�ж���ģ�����νUnicode�ַ��飬
	 * ����һ�������Unicode�ַ��ļ��ϡ����磬�������ַ������������ַ�����ͷ���ŵȵȡ������ʾ���������ʹ�ӡ����ϣ���ַ����а����������ַ���ֵ��
	 */
	@Test
	public void test6()

	{

		int j = 0;

		System.out.println("No " + "DecNumber " + "HexNumber " + "CharValue");

		for (int i = 0; i <= 0xffff; i++)

		{

			// ����ַ�����ϣ���ַ���

			if (Character.UnicodeBlock.of((char) i) == Character.UnicodeBlock.GREEK)

			{

				++j;

				// ��ӡ����Ӧ��ʮ������ֵ

				System.out.println(j + " " + i + " " + Integer.toHexString(i)
						+ " " + (char) i);

			}

		}

	}

	/*
	 * Java �� Character �� digit �� forDigit �����������ġ�
	 * 
	 * public static int digit(char ch, int radix) public static char
	 * forDigit(int digit, int radix)
	 * 
	 * ������һ�������Ƿ���ֱ radix �������ϵ���˼�ǻ��������� JDK ��CHM
	 * Ҳû���������������ʲô��˼������֮�󣬷�����������������ɽ��ơ������ƾ��� 2 ���˽��ƾ���8��ʮ�����ƾ���16�� radix ��СֵΪ
	 * MIN_RADIX (2) �����ֵΪ MAX_RADIX (36)�� ��ֵ���� 0~9,A~Z ����ʾ���ַ���Сд��һ���ġ�
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
	 * �����ݻ������ص�ǰ�ַ���ֵ��ʮ���ơ����������Character.MIN_RADIX <= radix <=
	 * Character.MAX_RADIX�����ߣ�ch����radix�����е���Чֵ������"-1"�����ch�ǡ���д����A��Z֮�䣬�򷵻�ch -
	 * 'A' + 10 ��ֵ������ǡ�Сд��a��z֮�䣬����ch - 'a' + 10 ��ֵ��
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
	 * �������ض������жϵ�ǰ��ֵ��ʾ���ַ���4�������㣬�Ƿ���ֵʱ���ء�'\u0000'����-
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
