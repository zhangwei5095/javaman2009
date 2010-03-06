【IT168 编程开发】对于很多应用系统，常常需要动态装载和执行类和代码片断，这有利于部署的简易性和系统设计上的灵活性。本文给出了一个比较全面的介绍，值得参考。

    在Sun JDK 1.2及后续版本中，包含了一组可在程序运行时刻编译和执行Java代码的API。这些API被包含在tools.jar类库中。这个功能允许Java程序在运行时动态编译、执行小的代码块，在有些情况下这个功能会让Java应用程序的架构更加灵活、开放。

    本文假定读者已经在计算机中安装并配置好了Sun JDK 1.2或更高的版本，并对javac编译器命令有所了解。

    在Java程序中使用编译器
    假定要使用javac命令编译 /home/mytest目录下Test.java文件，并设定class文件存放在/home/mytest/classes路径下，输入下面命令：

    javac -d /home/mytest/classes Test.java
    达到同样的目的，也可以使用Sun提供的一个Java编译器的API来实现。它的使用也很简单，核心代码段如下：

    …
    String[] args = new String[] {“-d”， “/homemytestclasses”， “Test.java”};
    Int status = javac.compile(args);
    …
    javac编译工具被安装在JDK根目录的/bin目录下，负责将源代码编译成运行于JVM的字节码。事实上，我们经常使用/bin目录下的javac编译工具来编译Java源文件。如果在Java程序中动态编译任意制定的Java语句，使用这个外部的javac编译器就显得不够灵活了。虽然有时可使用Runtime类来执行一个外部命令，但如果想知道代码是否被编译通过、编译时发生了什么错误，用Runtime类的exec()方法就很难实现了。

    在Sun的JDK 1.2及后续版本中，JDK安装路径的/lib路径下包含了一个tools.jar文件，这个类库包含了一个完整的编译器包。com.sun.tools.javac.Main是编译器的主类入口，如果已经熟悉了javac编译器命令行的使用方法，很容易理解这个类的使用方法。方法compile(String[] p)执行编译动作，参数p是一个String数组，用来存放javac命令的参数选项，编译后的状态返回一个Int值，其对应值参考如下表所示：

    表 状态参数与对应值
    EXIT_OK 0
    EXIT_ERROR 1
    EXIT_CMDERR 2
    EXIT_SYSERR 3
    EXIT_ABNORMAL 4
    在程序执行时编译和执行Java语句
    从上面一段中，我们已经基本了解了动态编译一个Java文件的方法。那么，如何运行时动态编译指定的Java语句呢？这里需要一个技巧。

    假设要动态编译的Java条语句如下：

    System.out.println(“Hello，This runtime code！”);
    编译器不支持编译单个Java语句，被编译的对象必须是一个以.java为后缀的、结构合法的类源程序文件，所以需要对这个语句进行改造，变成一个完整的类，并把这条语句置入main方法中，便于测试。

    public class <临时类文件名> {
    public static void main(String[] args) throws Exception {
    System.out.println(“Hello，This runtime code！”);
    }
    }
    这样，欲动态编译的代码已经被程序动态拼装成了上面那段代码，准备工作还没有结束，不过看起来工作在趋向稍微的复杂化。因为上述代码当前还存放在内存中，编译器似乎对一个硬盘文件更感兴趣。我们需要引用java.io.File类（JDK 1.2以上），创建一个临时的文件来存放上述代码的内容。java.io.File类的静态方法createTempFile()方法保证所创建的文件名是不重复的，这样会增大这段程序的灵活性。灵活性取决于真正应用到系统架构中的策略。

    System.getProperty(“user.dir”)用来获得当前路径，在这里作为临时文件的存放目录。

    File file;
    file = File.createTempFile(“JavaRuntime”， “.java”， new File(System.getProperty(“user.dir”)));
    String filename = file.getName();
    String classname = getClassName(filename);
    //将代码输出到文件
    PrintWriter out = new PrintWriter(new FileOutputStream(file));
    out.println(“public class” + classname + “ {”};
    out.println(“..代码..”);
    out.println(“}”);
    //关闭文件流
    out.flush();
    out.close();
    我们约定被创建的临时文件名以“JavaRuntime”为头缀（可任意命名），后缀名以“.java”结尾。一个待编译的Java源文件已被动态生成。下一步要从com.sun.tools.javac包中创建一个Main实例，调用javac.compile()方法编译这个临时文件：

    Private static com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
    String[] args = new String[] {“-d”， System.getProperty(“user.dir”)，filename };
    Int status = javac.compile(args);
    假定临时文件通过了编译器文法验证等验证，编译成功（status值等于0，参看前表），在当前程序的运行目录下就会多了一个Java类文件。我们将通过执行这个Java 类文件，来模拟执行欲动态编译代码的结果。

    Java提供在运行时刻加载类的特性，可动态识别和调用类构造方法、类字段和类方法。java.lang.reflect.Method实现了Member接口，可以调用接口的方法来获得方法类的名称、修饰词等。方法getRuturnType()、getParameterTypes()、getExeptionTypess()等返回被表示方法的构造信息。Method另一个重要的特性是可以调用invoke()执行这个方法（详细使用方法可以查看java.lang.reflect包文档）。下面这段代码中创建一个java.lang.reflect.Method类方法，调用getMethod()方法获得被拼装的main方法的映射，这段代码如下：

    try {
    // 访问这个类
    Class cls = Class.forName(classname);
    //调用main方法
    Method main = cls.getMethod(“main”， new Class[] { String[].class });
    main.invoke(null， new Object[] { new String[0] });
    }catch (SecurityException se) {
    debug(“access to the information is denied:” + se.toString());
    }catch (NoSuchMethodException nme) {
    debug(“a matching method is not found or if then name is or :

    ” + nme.toString());
    }catch (InvocationTargetException ite) {
    debug(“Exception in main: ” + ite.getTargetException());
    }catch (Exception e){
    debug(e.toString());
    }
    运行结果参如下：

    Hello，This runtime code！

    示范程序
    下面给出了一个简单的Java程序，这个程序说明了如何利用Sun的javac编译器完成动态编译Java语句。运行该程序需要计算机安装JDK 1.2以上版本，并在classpath中或运行时指定tools.jar文件位置。

    程序结构：

    ◆ compile() 编译Java代码，返回生成的临时文件；
    ◆ run()运行编译的class文件；
    ◆ debug()输出调试信息；
    ◆ getClassName()从一个Java源文件获得类名；
    ◆ readLine()从控制台读取用户输入的Java Code。

    Import java.io.File;
    …
    Public class RuntimeCode{
    /**编译器*/
    private static com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
    /**等待用户输入JavaCode，然后编译、执行*/
    public static void main(String[] args) throws Exception{
    …
    run(compile(code));
    }
    /**编译JavaCode，返回临时文件对象*/
    private synchronized static File compile(String code)

    throws IOException，Exception {
    File file;
    //在用户当前文件目录创建一个临时代码文件
    file = File.createTempFile(“JavaRuntime”， “.java”，
    new File(System.getProperty(“user.dir”)));
    //当虚拟机退出时，删除此临时java源文件
    file.deleteOnExit();
    //获得文件名和类名字
    String filename = file.getName();
    String classname = getClassName(filename);
    //将代码输出到文件
    PrintWriter out = new PrintWriter(new FileOutputStream(file));
    out.println(“/**”);
    …
    //关闭文件流
    out.flush();
    out.close();
    //编译代码文件
    String[] args = new String[] {“-d”， System.getProperty(“user.dir”)，filename };
    //返回编译的状态代码
    int status = javac.compile(args);
    //处理编译状态
    …
    }
    /**执行刚刚编译的类文件*/
    private static synchronized void run(File file)

    …
    //当虚拟机退出时，删除此临时编译的类文件
    new File(file.getParent()， classname + “.class”).deleteOnExit();
    try {
    // 访问这个类
    Class cls = Class.forName(classname);
    //映射main方法
    Method main = cls.getMethod(“main”， new Class[] { String[].class });
    //执行main方法
    main.invoke(null， new Object[] { new String[0] });
    }catch (SecurityException se) {
    …
    }
    }
    /**打印调试信息*/
    private static void debug(String msg) {
    System.err.println(msg);
    }
    /**根据一个java源文件名获得类名*/
    private static String getClassName(String filename){
    return filename.substring(0，filename.length()-5);
    }
    /**从控制台获得用户输入的Java代码段*/
    …
    }
    编译运行上述代码，在please input java code提示下输入以下代码：

    for(int i=0;i<10;i++){System.out.println(“this is:”+i);}
    运行结果如下所示：

    Please input java code：

    for(int i=0;i<10;i++){System.out.println(“this is:”+i);}
    wait....
    --------------------
    this is:0
    this is:1
    this is:2
    this is:3
    this is:4
    this is:5
    this is:6
    this is:7
    this is:8
    this is:9
    总结
    在大中型企业应用系统平台中，使用代码动态编译技术结合OO编程模型，可在系统不菪机条件下保证系统的可扩展性和伸缩性。如果你是一个Java程序员，稍加调整以上代码，还可以帮助调试小段的Java代码．

