欢迎进入Java社区论坛，与200万技术人员互动交流 >>进入


4．Character类的使用方法


Character：字符类型

１、属性。

static int MIN_RADIX ：返回最小基数。
static int MAX_RADIX ：返回最大基数。
static char MAX_VALUE ：字符类型的最大值。
static char MIN_VALUE ：字符类型的最小值。
static Class TYPE ：返回当前类型。

２、构造函数。

Character(char value)：以char参数构造一个Character对象。

３、方法。
说明：
1. 所有方法均为public；
2. 书写格式：［修饰符］　<返回类型> <方法名（［参数列表］）>
如：
static int parseInt(String s) 表示：此方法（parseInt）为类方法（static），返回类型为（int），方法所需参数为String类型。

1. char charValue() ：返回字符对象的值。
2. int compareTo(Character anotherCharacter) ：当前Character对象与anotherCharacter比较。相等关系返回０；小于关系返回负数；大于关系返回正数。
3. int compareTo(Object o) ：当前对象与另一个对象进行比较。如果o是Character对象，则与2功能一样；否则，抛出ClassCastException异常。
4. static int digit(char ch, int radix) ：根据基数返回当前字符的值的十进制。如果不满足Character.MIN_RADIX <= radix <= Character.MAX_RADIX，或者，ch不是radix基数中的有效值，返回"-1"；如果ch是“大写”的A到Z之间，则返回ch - 'A' + 10 的值；如果是“小写”a到z之间，返回ch - 'a' + 10 的值。

System.out.println("Character.MIN_RADIX: " + Character.MIN_RADIX );
System.out.println("Character.MAX_RADIX: " + Character.MAX_RADIX );
System.out.println("Character.digit('2',2): " + Character.digit('2',2) );
System.out.println("Character.digit('7',10): " + Character.digit('7',10) );
System.out.println("Character.digit('F',16): " + Character.digit('F',16) );
结果为：
Character.MIN_RADIX: 2
Character.MAX_RADIX: 36
Character.digit('2',2): -1
Character.digit('7',10): 7
Character.digit('F',16): 15

5. boolean equals(Object obj) ：与obj对象比较。当且仅当obj不为“null”并且和当前Character对象一致时返回“true”。
6. static char forDigit(int digit, int radix) ：根据特定基数判断当前数值表示的字符。4的逆运算，非法数值时返回“'\u0000'”。

System.out.println("Character.MIN_RADIX: " + Character.MIN_RADIX );
System.out.println("Character.MAX_RADIX: " + Character.MAX_RADIX );
System.out.println("Character.forDigit(2,2): " + Character.forDigit(2,2) );
System.out.println("Character.forDigit(7,10): " + Character.forDigit(7,10) );
System.out.println("Character.forDigit(15,16): " + Character.forDigit(15,16) );
结果为：
Character.MIN_RADIX: 2
Character.MAX_RADIX: 36
Character.forDigit(2,2): 
Character.forDigit(7,10): 7
Character.forDigit(15,16): f


7. static int getNumericValue(char ch) ：返回字符ch的数值。
8. static int getType(char ch) ：返回字符所属类型。具体有哪些种类请查看Java文档资料。
9. int hashCode() ：返回当前字符的哈希表码。
10. static boolean isDefined(char ch) ：判断字符ch在Unicode字符集是否用明确定义。
11. static boolean isDigit(char ch) ：判断字符ch是否为数字。
12. static boolean isIdentifierIgnorable(char ch) ：判断字符ch是否为Unicode字符集中可忽略的字符。
13. static boolean isISOControl(char ch) ：判断字符ch是否为ISO标准中的控制字符。
14.static boolean isJavaIdentifierPart(char ch) ：判断字符ch是否为Java中的部分标识符。
15. static boolean isJavaIdentifierStart(char ch) ：判断字符ch是否为Java中的第一个标识符。
16. static boolean isLetter(char ch) ：判断字符ch是否为字母。
17. static boolean isLetterOrDigit(char ch) ：判断字符ch是否为字母或数字。
18. static boolean isLowerCase(char ch) ：判断字符ch是否为小写字母。
19. static boolean isMirrored(char c) ：根据Unicode表判断字符c是否存在与之方向相反的字符。例如：“［”存在与之方向相反的“］”，结果为：true。
20. static boolean isSpaceChar(char ch) ：判断字符ch是否为Unicode中的空格。
21. static boolean isUpperCase(char ch) ：判断字符ch是否为大写字母。
22. static boolean isWhitespace(char ch) ：判断字符ch是否为Java定义中的空字符。
其中包括： 
    char c1 = '\u0009';//水平列表符
    char c2 = '\u000A';//换行
    char c3 = '\u000B';//垂直列表符
    char c4 = '\u000C';//换页
    char c5 = '\u000D';//回车
    char c6 = '\u001C';//文件分隔符
    char c7 = '\u001D';//组分隔符
    char c8 = '\u001E';//记录分隔符
    char c9 = '\u001F';//单元分隔符

23. static char toLowerCase(char ch) ：转换ch是否为小写。
24. String toString() ：将当前Character对象转换成字符串。
25. static String toString(char c) ：此为类方法，将c转换成字符串。
26. static char toUpperCase(char ch) ：转换ch是否为大写。

System.out.println("Character.toUpperCase('q'): " + Character.toUpperCase('q') );
System.out.println("Character.toLowerCaseCase('B'): " + Character.toLowerCase('B') );
结果为：
Character.toUpperCase('q'): Q
Character.toLowerCaseCase('B'): b
