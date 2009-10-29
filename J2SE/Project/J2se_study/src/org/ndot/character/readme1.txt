��ӭ����Java������̳����200������Ա�������� >>����


4��Character���ʹ�÷���


Character���ַ�����

�������ԡ�

static int MIN_RADIX ��������С������
static int MAX_RADIX ��������������
static char MAX_VALUE ���ַ����͵����ֵ��
static char MIN_VALUE ���ַ����͵���Сֵ��
static Class TYPE �����ص�ǰ���͡�

�������캯����

Character(char value)����char��������һ��Character����

����������
˵����
1. ���з�����Ϊpublic��
2. ��д��ʽ�������η��ݡ�<��������> <���������۲����б�ݣ�>
�磺
static int parseInt(String s) ��ʾ���˷�����parseInt��Ϊ�෽����static������������Ϊ��int���������������ΪString���͡�

1. char charValue() �������ַ������ֵ��
2. int compareTo(Character anotherCharacter) ����ǰCharacter������anotherCharacter�Ƚϡ���ȹ�ϵ���أ���С�ڹ�ϵ���ظ��������ڹ�ϵ����������
3. int compareTo(Object o) ����ǰ��������һ��������бȽϡ����o��Character��������2����һ���������׳�ClassCastException�쳣��
4. static int digit(char ch, int radix) �����ݻ������ص�ǰ�ַ���ֵ��ʮ���ơ����������Character.MIN_RADIX <= radix <= Character.MAX_RADIX�����ߣ�ch����radix�����е���Чֵ������"-1"�����ch�ǡ���д����A��Z֮�䣬�򷵻�ch - 'A' + 10 ��ֵ������ǡ�Сд��a��z֮�䣬����ch - 'a' + 10 ��ֵ��

System.out.println("Character.MIN_RADIX: " + Character.MIN_RADIX );
System.out.println("Character.MAX_RADIX: " + Character.MAX_RADIX );
System.out.println("Character.digit('2',2): " + Character.digit('2',2) );
System.out.println("Character.digit('7',10): " + Character.digit('7',10) );
System.out.println("Character.digit('F',16): " + Character.digit('F',16) );
���Ϊ��
Character.MIN_RADIX: 2
Character.MAX_RADIX: 36
Character.digit('2',2): -1
Character.digit('7',10): 7
Character.digit('F',16): 15

5. boolean equals(Object obj) ����obj����Ƚϡ����ҽ���obj��Ϊ��null�����Һ͵�ǰCharacter����һ��ʱ���ء�true����
6. static char forDigit(int digit, int radix) �������ض������жϵ�ǰ��ֵ��ʾ���ַ���4�������㣬�Ƿ���ֵʱ���ء�'\u0000'����

System.out.println("Character.MIN_RADIX: " + Character.MIN_RADIX );
System.out.println("Character.MAX_RADIX: " + Character.MAX_RADIX );
System.out.println("Character.forDigit(2,2): " + Character.forDigit(2,2) );
System.out.println("Character.forDigit(7,10): " + Character.forDigit(7,10) );
System.out.println("Character.forDigit(15,16): " + Character.forDigit(15,16) );
���Ϊ��
Character.MIN_RADIX: 2
Character.MAX_RADIX: 36
Character.forDigit(2,2): 
Character.forDigit(7,10): 7
Character.forDigit(15,16): f


7. static int getNumericValue(char ch) �������ַ�ch����ֵ��
8. static int getType(char ch) �������ַ��������͡���������Щ������鿴Java�ĵ����ϡ�
9. int hashCode() �����ص�ǰ�ַ��Ĺ�ϣ���롣
10. static boolean isDefined(char ch) ���ж��ַ�ch��Unicode�ַ����Ƿ�����ȷ���塣
11. static boolean isDigit(char ch) ���ж��ַ�ch�Ƿ�Ϊ���֡�
12. static boolean isIdentifierIgnorable(char ch) ���ж��ַ�ch�Ƿ�ΪUnicode�ַ����пɺ��Ե��ַ���
13. static boolean isISOControl(char ch) ���ж��ַ�ch�Ƿ�ΪISO��׼�еĿ����ַ���
14.static boolean isJavaIdentifierPart(char ch) ���ж��ַ�ch�Ƿ�ΪJava�еĲ��ֱ�ʶ����
15. static boolean isJavaIdentifierStart(char ch) ���ж��ַ�ch�Ƿ�ΪJava�еĵ�һ����ʶ����
16. static boolean isLetter(char ch) ���ж��ַ�ch�Ƿ�Ϊ��ĸ��
17. static boolean isLetterOrDigit(char ch) ���ж��ַ�ch�Ƿ�Ϊ��ĸ�����֡�
18. static boolean isLowerCase(char ch) ���ж��ַ�ch�Ƿ�ΪСд��ĸ��
19. static boolean isMirrored(char c) ������Unicode���ж��ַ�c�Ƿ������֮�����෴���ַ������磺���ۡ�������֮�����෴�ġ��ݡ������Ϊ��true��
20. static boolean isSpaceChar(char ch) ���ж��ַ�ch�Ƿ�ΪUnicode�еĿո�
21. static boolean isUpperCase(char ch) ���ж��ַ�ch�Ƿ�Ϊ��д��ĸ��
22. static boolean isWhitespace(char ch) ���ж��ַ�ch�Ƿ�ΪJava�����еĿ��ַ���
���а����� 
    char c1 = '\u0009';//ˮƽ�б��
    char c2 = '\u000A';//����
    char c3 = '\u000B';//��ֱ�б��
    char c4 = '\u000C';//��ҳ
    char c5 = '\u000D';//�س�
    char c6 = '\u001C';//�ļ��ָ���
    char c7 = '\u001D';//��ָ���
    char c8 = '\u001E';//��¼�ָ���
    char c9 = '\u001F';//��Ԫ�ָ���

23. static char toLowerCase(char ch) ��ת��ch�Ƿ�ΪСд��
24. String toString() ������ǰCharacter����ת�����ַ�����
25. static String toString(char c) ����Ϊ�෽������cת�����ַ�����
26. static char toUpperCase(char ch) ��ת��ch�Ƿ�Ϊ��д��

System.out.println("Character.toUpperCase('q'): " + Character.toUpperCase('q') );
System.out.println("Character.toLowerCaseCase('B'): " + Character.toLowerCase('B') );
���Ϊ��
Character.toUpperCase('q'): Q
Character.toLowerCaseCase('B'): b
