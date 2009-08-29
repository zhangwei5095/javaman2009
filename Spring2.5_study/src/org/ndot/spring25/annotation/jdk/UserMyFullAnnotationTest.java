package org.ndot.spring25.annotation.jdk;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：StuJ2SE
 * 
 * 文件名： UserMyFullAnnotationTest.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-10
 * 
 */
public class UserMyFullAnnotationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	@SuppressWarnings( { "unused", "unchecked" })
	public void testCheckAnnotation() {
		try {
			UserMyFullAnnotation u = new UserMyFullAnnotation();
			Annotation[] ans = u.getClass().getAnnotations();
			for (Annotation annotation : ans) {
				System.out
						.println("UserMyFullAnnotation.class use Annotation is : "
								+ annotation.toString());
			}

			Method[] md = u.getClass().getDeclaredMethods();// .getMethods();
			for (Method method : md) {
				Annotation[] mans = method.getAnnotations();
				System.out.println("当前方法:" + method.getName());
				System.out.println("当前方法的注释: ");
				for (Annotation annotation : mans) {
					System.out.println(annotation.toString());
					Class annotationClazz = annotation.annotationType();
					if (annotationClazz.equals(MyFullAnnotation.class)) {
						MyFullAnnotation an = (MyFullAnnotation) annotation;
						int age = an.age();
						String name = an.name();
						String[] tel = an.tel();
						Deprecated dp = an.de();
						Class[] clazz = an.exceptons();
						MyFullAnnotation.Color color = an.color();

						System.out.println("age=" + age);
						System.out.println("name=" + name);
						System.out.print("tel=");
						for (String t : tel) {
							System.out.print(t + "||");
						}
						System.out.println();
						System.out.println("dp=" + dp);
						System.out.print("clazz=");
						for (Class c : clazz) {
							System.out.print(c.getName() + "||");
						}
						System.out.println();
						if (color.equals(MyFullAnnotation.Color.YELLOW)) {
							Method m = u.getClass().getMethod("setMessage",
									String.class);
							m.invoke(u, name);
						}
						u.checkAnnotation();

					}
				}
			}
			// Method md = u.getClass().getMethod("checkAnnotation");
			//
			// if (md.isAnnotationPresent(MyFullAnnotation.class)) {
			// MyFullAnnotation an = (MyFullAnnotation) md
			// .getAnnotation(MyFullAnnotation.class);
			// int age = an.age();
			// String name = an.name();
			// String[] tel = an.tel();
			// Deprecated dp = an.de();
			// Class[] clazz = an.exceptons();
			// MyFullAnnotation.Color color = an.color();
			//
			// System.out.println("age=" + age);
			// System.out.println("name=" + name);
			// System.out.print("tel=");
			// for (String t : tel) {
			// System.out.print(t + "||");
			// }
			// System.out.println();
			// System.out.println("dp=" + dp);
			// System.out.print("clazz=");
			// for (Class c : clazz) {
			// System.out.print(c.getName() + "||");
			// }
			// System.out.println();
			// if (color.equals(MyFullAnnotation.Color.YELLOW)) {
			// Method m = u.getClass().getMethod("setMessage",
			// String.class);
			// m.invoke(u, name);
			// }
			// u.checkAnnotation();
			//
			// }

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
