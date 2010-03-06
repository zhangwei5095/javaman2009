前言，在Java运行时刻，能否知道一个类的属性方法并调用改动之？对于任意一个对象，能否知道他的所属类，并调用他的方法？答案是肯定的。这种动态的获取信息及动态调用方法的机制在Java中称为“反射”（reflection）。 



Java反射机制主要提供以下功能： 



在运行时判断任意一个对象所属的类； 
在运行时构造任意一个类的对象； 
在运行时判断任意一个类所具有的成员变量和方法； 
在运行时调用任意一个对象的方法。 

Reflection 是Java被视为动态（或准动态）语言的一个关键性质。这个机制允许程序在运行时透过Reflection APIs取得任何一个已知名称的class的内部信息，包括其modifiers（诸如public, static 等等）、superclass（例如Object）、实现之interfaces（例如Serializable），也包括fields和methods的所有信息，并可于运行时改变fields内容或调用methods。 



一般而言，开发者社群说到动态语言，大致认同的一个定义是：“程序运行时，允许改变程序结构或变量类型，这种语言称为动态语言”。 



在JDK中，主要由以下类来实现Java反射机制，这些类都位于java.lang.reflect包中： 
Class类：代表一个类； 
Field 类：代表类的成员变量（成员变量也称为类的属性）； 
Method类：代表类的方法； 
Constructor 类：代表类的构造方法； 
Array类：提供了动态创建数组，以及访问数组的元素的静态方法； 



例程DateMethodsTest类演示了Reflection API的基本作用，它读取命令行参数指定的类名，然后打印这个类所具有的方法信息，代码如下： 

Datemethodstest.java代码 
public class DateMethodsTest      
{      
    public static void main(String args[]) throws Exception      
    {      
        // 加载并初始化命令行参数指定的类      
        Class<?> classType = Class.forName("java.util.Date");      
        // 获得类的所有方法      
        Method methods[] = classType.getDeclaredMethods();      
        for (int i = 0; i < methods.length; i++)      
        {      
            System.out.println(methods[i].toString());      
        }      
    }      
}    

public class DateMethodsTest   
{   
    public static void main(String args[]) throws Exception   
    {   
        // 加载并初始化命令行参数指定的类   
        Class<?> classType = Class.forName("java.util.Date");   
        // 获得类的所有方法   
        Method methods[] = classType.getDeclaredMethods();   
        for (int i = 0; i < methods.length; i++)   
        {   
            System.out.println(methods[i].toString());   
        }   
    }   
}  


例程ReflectTester类进一步演示了Reflection API的基本使用方法。ReflectTester类有一个copy(Object object)方法，这个方法能够创建一个和参数object同样类型的对象，然后把object对象中的所有属性拷贝到新建的对象中，并将它返回这个例子只能复制简单的JavaBean，假定JavaBean 的每个属性都有public 类型的getXXX()和setXXX()方法，代码如下： 

Reflecttester.java代码 
public class ReflectTester {      
    public Object copy(Object object) throws Exception {      
        // 获得对象的类型      
        Class<?> classType = object.getClass();      
        System.out.println("Class:" + classType.getName());      
     
        // 通过默认构造方法创建一个新的对象      
        Object objectCopy = classType.getConstructor(new Class[] {}).newInstance(new Object[] {});      
     
        // 获得对象的所有属性      
        Field fields[] = classType.getDeclaredFields();      
     
        for (int i = 0; i < fields.length; i++) {      
            Field field = fields[i];      
     
            String fieldName = field.getName();      
            String firstLetter = fieldName.substring(0, 1).toUpperCase();      
            // 获得和属性对应的getXXX()方法的名字      
            String getMethodName = "get" + firstLetter + fieldName.substring(1);      
            // 获得和属性对应的setXXX()方法的名字      
            String setMethodName = "set" + firstLetter + fieldName.substring(1);      
     
            // 获得和属性对应的getXXX()方法      
            Method getMethod = classType.getMethod(getMethodName, new Class[] {});      
            // 获得和属性对应的setXXX()方法      
            Method setMethod = classType.getMethod(setMethodName, new Class[] { field.getType() });      
     
            // 调用原对象的getXXX()方法      
            Object value = getMethod.invoke(object, new Object[] {});      
            System.out.println(fieldName + ":" + value);      
            // 调用拷贝对象的setXXX()方法      
            setMethod.invoke(objectCopy, new Object[] { value });      
        }      
        return objectCopy;      
    }      
     
    public static void main(String[] args) throws Exception {      
        Customer customer = new Customer("Tom", 21);      
        customer.setId(new Long(1));      
     
        Customer customerCopy = (Customer) new ReflectTester().copy(customer);      
        System.out.println("Copy information:" + customerCopy.getId() + " "     
                + customerCopy.getName() + " " + customerCopy.getAge());      
    }      
}      
     
class Customer {      
    private Long id;      
     
    private String name;      
     
    private int age;      
     
    public Customer() {      
    }      
     
    public Customer(String name, int age) {      
        this.name = name;      
        this.age = age;      
    }      
     
    public Long getId() {      
        return id;      
    }      
     
    public void setId(Long id) {      
        this.id = id;      
    }      
     
    public String getName() {      
        return name;      
    }      
     
    public void setName(String name) {      
        this.name = name;      
    }      
     
    public int getAge() {      
        return age;      
    }      
     
    public void setAge(int age) {      
        this.age = age;      
    }      
}   

public class ReflectTester {   
    public Object copy(Object object) throws Exception {   
        // 获得对象的类型   
        Class<?> classType = object.getClass();   
        System.out.println("Class:" + classType.getName());   
  
        // 通过默认构造方法创建一个新的对象   
        Object objectCopy = classType.getConstructor(new Class[] {}).newInstance(new Object[] {});   
  
        // 获得对象的所有属性   
        Field fields[] = classType.getDeclaredFields();   
  
        for (int i = 0; i < fields.length; i++) {   
            Field field = fields[i];   
  
            String fieldName = field.getName();   
            String firstLetter = fieldName.substring(0, 1).toUpperCase();   
            // 获得和属性对应的getXXX()方法的名字   
            String getMethodName = "get" + firstLetter + fieldName.substring(1);   
            // 获得和属性对应的setXXX()方法的名字   
            String setMethodName = "set" + firstLetter + fieldName.substring(1);   
  
            // 获得和属性对应的getXXX()方法   
            Method getMethod = classType.getMethod(getMethodName, new Class[] {});   
            // 获得和属性对应的setXXX()方法   
            Method setMethod = classType.getMethod(setMethodName, new Class[] { field.getType() });   
  
            // 调用原对象的getXXX()方法   
            Object value = getMethod.invoke(object, new Object[] {});   
            System.out.println(fieldName + ":" + value);   
            // 调用拷贝对象的setXXX()方法   
            setMethod.invoke(objectCopy, new Object[] { value });   
        }   
        return objectCopy;   
    }   
  
    public static void main(String[] args) throws Exception {   
        Customer customer = new Customer("Tom", 21);   
        customer.setId(new Long(1));   
  
        Customer customerCopy = (Customer) new ReflectTester().copy(customer);   
        System.out.println("Copy information:" + customerCopy.getId() + " "  
                + customerCopy.getName() + " " + customerCopy.getAge());   
    }   
}   
  
class Customer {   
    private Long id;   
  
    private String name;   
  
    private int age;   
  
    public Customer() {   
    }   
  
    public Customer(String name, int age) {   
        this.name = name;   
        this.age = age;   
    }   
  
    public Long getId() {   
        return id;   
    }   
  
    public void setId(Long id) {   
        this.id = id;   
    }   
  
    public String getName() {   
        return name;   
    }   
  
    public void setName(String name) {   
        this.name = name;   
    }   
  
    public int getAge() {   
        return age;   
    }   
  
    public void setAge(int age) {   
        this.age = age;   
    }   
}  

ReflectTester 类的copy(Object object)方法依次执行以下步骤： 


（1）获得对象的类型： 
Class classType=object.getClass(); 
System.out.println("Class:"+classType.getName()); 



在java.lang.Object 类中定义了getClass()方法，因此对于任意一个Java对象，都可以通过此方法获得对象的类型。Class类是Reflection API 中的核心类，它有以下方法： 
getName()：获得类的完整名字； 
getFields()：获得类的public类型的属性； 
getDeclaredFields()：获得类的所有属性； 
getMethods()：获得类的public类型的方法； 
getDeclaredMethods()：获得类的所有方法； 



getMethod(String name, Class[] parameterTypes)：获得类的特定方法，name参数指定方法的名字，parameterTypes 参数指定方法的参数类型； 

getConstructors()：获得类的public类型的构造方法； 
getConstructor(Class[] parameterTypes)：获得类的特定构造方法，parameterTypes 参数指定构造方法的参数类型； 
newInstance()：通过类的不带参数的构造方法创建这个类的一个对象； 



（2）通过默认构造方法创建一个新对象： 
Object objectCopy=classType.getConstructor(new Class[]{}).newInstance(new Object[]{}); 
以上代码先调用Class类的getConstructor()方法获得一个Constructor 对象，它代表默认的构造方法，然后调用Constructor对象的newInstance()方法构造一个实例。 



（3）获得对象的所有属性： 
Field fields[]=classType.getDeclaredFields(); 
Class 类的getDeclaredFields()方法返回类的所有属性，包括public、protected、默认和private访问级别的属性。 



（4）获得每个属性相应的getXXX()和setXXX()方法，然后执行这些方法，把原来对象的属性拷贝到新的对象中。 



以上是一个反射（reflection）的比较详细的解说，当然真正工程上是不会这样麻烦的，这里是底层的讲解。 





以下这个类运用反射机制调用其add()和echo()方法，代码如下： 

Invoketester.java代码 
import java.lang.reflect.Method;      
     
public class InvokeTester {      
    public int add(int param1, int param2) {      
        return param1 + param2;      
    }      
     
    public String echo(String msg) {      
        return "echo: " + msg;      
    }      
     
    public static void main(String[] args) throws Exception {      
        Class<?> classType = InvokeTester.class;      
        Object invokeTester = classType.newInstance();      
     
        // 调用InvokeTester对象的add()方法      
        Method addMethod = classType.getMethod("add", new Class[] { int.class,      
                int.class });      
        Object result = addMethod.invoke(invokeTester, new Object[] {      
                new Integer(100), new Integer(200) });      
        System.out.println((Integer) result);      
     
        // 调用InvokeTester对象的echo()方法      
        Method echoMethod = classType.getMethod("echo",      
                new Class[] { String.class });      
        result = echoMethod.invoke(invokeTester, new Object[] { "Hello" });      
        System.out.println((String) result);      
    }      
}    

import java.lang.reflect.Method;   
  
public class InvokeTester {   
    public int add(int param1, int param2) {   
        return param1 + param2;   
    }   
  
    public String echo(String msg) {   
        return "echo: " + msg;   
    }   
  
    public static void main(String[] args) throws Exception {   
        Class<?> classType = InvokeTester.class;   
        Object invokeTester = classType.newInstance();   
  
        // 调用InvokeTester对象的add()方法   
        Method addMethod = classType.getMethod("add", new Class[] { int.class,   
                int.class });   
        Object result = addMethod.invoke(invokeTester, new Object[] {   
                new Integer(100), new Integer(200) });   
        System.out.println((Integer) result);   
  
        // 调用InvokeTester对象的echo()方法   
        Method echoMethod = classType.getMethod("echo",   
                new Class[] { String.class });   
        result = echoMethod.invoke(invokeTester, new Object[] { "Hello" });   
        System.out.println((String) result);   
    }   
}   

add()方法的两个参数为int 类型，获得表示add()方法的Method对象的代码如下： 
Method addMethod=classType.getMethod("add",new Class[]{int.class,int.class}); 
Method类的invoke(Object obj,Object args[])方法接收的参数必须为对象，如果参数为基本类型数据，必须转换为相应的包装类型的对象。invoke()方法的返回值总是对象，如果实际被调用的方法的返回类型是基本类型数据，那么invoke()方法会把它转换为相应的包装类型的对象，再将其返回。 



在本例中，尽管InvokeTester 类的add()方法的两个参数以及返回值都是int类型，调用add Method 对象的invoke()方法时，只能传递Integer 类型的参数，并且invoke()方法的返回类型也是Integer 类型，Integer 类是int 基本类型的包装类： 

Object result=addMethod.invoke(invokeTester, 
new Object[]{new Integer(100),new Integer(200)}); 
System.out.println((Integer)result); //result 为Integer类型。 



java.lang.Array 类提供了动态创建和访问数组元素的各种静态方法。下面的例子ArrayTester1.java的main()方法创建了一个长度为10 的字符串数组，接着把索引位置为5 的元素设为“hello”，然后再读取索引位置为5 的元素的值，代码如下： 

Arraytester1.java代码 
import java.lang.reflect.Array;      
     
public class ArrayTester1      
{      
    public static void main(String args[]) throws Exception      
    {      
        Class<?> classType = Class.forName("java.lang.String");      
        // 创建一个长度为10的字符串数组      
        Object array = Array.newInstance(classType, 10);      
        // 把索引位置为5的元素设为"hello"      
        Array.set(array, 5, "hello");      
        // 获得索引位置为5的元素的值      
        String s = (String) Array.get(array, 5);      
        System.out.println(s);      
    }      
}   

import java.lang.reflect.Array;   
  
public class ArrayTester1   
{   
    public static void main(String args[]) throws Exception   
    {   
        Class<?> classType = Class.forName("java.lang.String");   
        // 创建一个长度为10的字符串数组   
        Object array = Array.newInstance(classType, 10);   
        // 把索引位置为5的元素设为"hello"   
        Array.set(array, 5, "hello");   
        // 获得索引位置为5的元素的值   
        String s = (String) Array.get(array, 5);   
        System.out.println(s);   
    }   
}  

Java API是个很好的帮助资料，大家可以查阅观看。比如说以上的例子：classType就是String类型的一个对象。通过Array类的newInstance(类型, 长度)创建了一个长度为10的，类型为String的Array数组。通过Array的get和set方法可以将字符串“hello”插进下标为5（实际上是Array中第六个元素）的“数组方块”中。 



下面例子是一个复杂的多维数组例子，不过只要了解了一维数组，应该不难了解多维数组的。所谓多维数组只是下级数组作为一个“元素”存在于上级数组中。 



例程ArrayTester2 类的main()方法创建了一个 5 x 10 x 15 的整型数组，并把索引位置为[3][5][10] 的元素的值为设37，代码如下： 

Arraytester2.java代码 
import java.lang.reflect.Array;      
     
public class ArrayTester2      
{      
    public static void main(String args[])      
    {      
        int[] dims = new int[] { 5, 10, 15 };      
        Object array = Array.newInstance(Integer.TYPE, dims);      
        Object arrayObj = Array.get(array, 3);      
        Class<?> cls = arrayObj.getClass().getComponentType();      
        System.out.println(cls);      
        arrayObj = Array.get(arrayObj, 5);      
        Array.setInt(arrayObj, 10, 37);      
        int arrayCast[][][] = (int[][][]) array;      
        System.out.println(arrayCast[3][5][10]);      
    }      
}   

import java.lang.reflect.Array;   
  
public class ArrayTester2   
{   
    public static void main(String args[])   
    {   
        int[] dims = new int[] { 5, 10, 15 };   
        Object array = Array.newInstance(Integer.TYPE, dims);   
        Object arrayObj = Array.get(array, 3);   
        Class<?> cls = arrayObj.getClass().getComponentType();   
        System.out.println(cls);   
        arrayObj = Array.get(arrayObj, 5);   
        Array.setInt(arrayObj, 10, 37);   
        int arrayCast[][][] = (int[][][]) array;   
        System.out.println(arrayCast[3][5][10]);   
    }   
}  Class是Reflection起源。针对任何您想探勘的class，唯有先为它产生一个Class object，接下来才能经由后者唤起为数十多个的Reflection APIs。 



Java允许我们从多种途径为一个class生成对应的Class object。 



（一）运用getClass()方法： 

String str = "abc"; 

Class class = str.getClass(); 



（二）运用Class.getSuperclass()方法： 

Button b = new Button(); 

Class c1 = b.getSuperclass(); 

Class c2 = c1.getSuperclass(); 



（三）运用静态方法Class.forName()，这个最常用： 

Class c1 = Class.forName("java.lang.String"); 

Class c2 = Class.forName("java.util.Date"); 



（四）运用.class语法： 

Class c1 = String.class; 

Class c2 = java.awt.Button.class; 

Class c3 = int.class; 

Class C4 = int[].class; 



（五）运用原始包装类的TYPE方法： 

Class c1 = Integer.TYPE; 

Class c2 = Character.TYPE; 

Class c3 = Boolean.TYPE; 

Class c4 = Void.TYPE; 
