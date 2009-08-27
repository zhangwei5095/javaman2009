package org.ndot.spring25.junit;

import org.junit.Test;
import org.ndot.spring25.People;
import org.ndot.spring25.container.NDotSimpleXMLApplicationContext;
import org.ndot.spring25.inject.BeanA;
import org.ndot.spring25.inject.BeanB;
import org.ndot.spring25.inject.NullBean;
import org.ndot.spring25.inject.dependson.Chicken;
import org.ndot.spring25.instance.Canada;
import org.ndot.spring25.instance.Chinise;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： Spring25Tester.java
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
 * 创建时间: 2009-8-20
 * 
 */
public class Spring25Tester {
	/**
	 * 简单的测试容器的创建和bean 的获取
	 */
	@Test
	public void simpleTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "beginApplicationContext.xml" });
			People p = (People) ctx.getBean("people");
			System.out.println(p.getName());
			System.out.println(p.getAddress().getCountry());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：利用自定义的IOC容器管理bean
	 * 
	 *<P>
	 */
	@Test
	public void ndotSimpleXMLApplicationContextTest() {
		try {
			NDotSimpleXMLApplicationContext ctx = new NDotSimpleXMLApplicationContext(
					new String[] { "beginApplicationContext.xml" });
			People p = (People) ctx.getBean("people");
			System.out.println(p.getName());
			System.out.println(p.getAddress().getCountry());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：测试实例化bean的三种方式
	 * 
	 *<P>
	 */

	@Test
	public void beanInstanceTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "beanInstanceApplicationContext.xml" });
			Canada canada = (Canada) ctx.getBean("canada");
			canada.say();

			Chinise chinise = (Chinise) ctx.getBean("chinise");
			chinise.say();

			Canada fcanada = (Canada) ctx.getBean("fcanada");
			fcanada.say();

			Chinise sfchinise = (Chinise) ctx.getBean("sfchinise");
			sfchinise.say();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：测试依赖注入功能
	 * 
	 *<P>
	 */
	@Test
	public void beanInjectTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "injectApplicationContext.xml" });
			{
				// 注入集合类型
				BeanA a1 = (BeanA) ctx.getBean("beanA1");

				System.out.println("=======================");
				System.out.println(a1.getIntp());
				System.out.println(a1.getStringp());
				a1.getBeanp().say();
				System.out.println("=======================");
				BeanA a2 = (BeanA) ctx.getBean("beanA2");
				System.out.println("=======================");
				System.out.println(a2.getIntp());
				System.out.println(a2.getStringp());
				a2.getBeanp().say();
				System.out.println("=======================");
				BeanA a3 = (BeanA) ctx.getBean("beanA3");
				System.out.println("=======================");
				System.out.println(a3.getIntp());
				System.out.println(a3.getStringp());
				a3.getBeanp().say();
				System.out.println("=======================");
				BeanA a4 = (BeanA) ctx.getBean("beanA4");
				System.out.println("=======================");
				System.out.println(a4.getIntp());
				System.out.println(a4.getStringp());
				a4.getBeanp().say();
				System.out.println("=======================");
				BeanA a5 = (BeanA) ctx.getBean("beanA4");
				System.out.println("=======================");
				System.out.println(a5.getIntp());
				System.out.println(a5.getStringp());
				a5.getBeanp().say();
				System.out.println("=======================");
			}
			{
				// 集合合并
				BeanB b1 = (BeanB) ctx.getBean("BeanBInject1");
				System.out.println("b1.getListp().size()="
						+ b1.getListp().size());
				BeanB b2 = (BeanB) ctx.getBean("BeanBInject2");
				System.out.println("b2.getListp().size()="
						+ b2.getListp().size());
			}
			{
				// 强制类型转换
				BeanB b3 = (BeanB) ctx.getBean("BeanBInject3");
				try {
					Float f1 = (Float) b3.getMapp().get("aaa");
					Float f2 = (Float) b3.getMapp().get("bbb");
					System.out.println(f1 + f2);
				} catch (Exception e) {
					System.err.println("========================");
					System.err.println("没有使用Spring的强制类型装换配置,数值计算异常......");
					System.err.println("========================");
				}
				System.out.println("使用Spring的强制类型装换配置,数值计算成功，结果如下：");
				System.out.println((b3.getFloatMap().get("aaa"))
						+ (b3.getFloatMap().get("bbb")));
			}
			{
				// Null 的配置
				NullBean nb = (NullBean) ctx.getBean("nullbean");
				System.out.println("nb.getNullValue()=" + nb.getNullValue());
				System.out.println("nb.getEmptyStr()+NDot =" + nb.getEmptyStr()
						+ "NDot");
			}
			{
				// 方法注入lookup
				System.out.println(((BeanA) ctx.getBean("beana"))
						.injectBeanBInstance().hashCode());
				System.out.println(((BeanA) ctx.getBean("beana"))
						.injectBeanBInstance().hashCode());

				System.out.println(((BeanA) ctx.getBean("beana"))
						.injectBeanBInstance().hashCode());


			}
			System.out.println("====恭喜您，全部测试成功啦：)=====");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：测试延迟加载和依赖实例化 这里的测试主要看 创建ApplicationContext容器实例
	 * 的时候配置bean的实例化过程，要观察日志的实例创建顺序
	 * 
	 * <1> .当Egg bean 没有对 Chicken2 bean 依赖的时候输出： Create Chicken Instance......
	 * Create Egg Instance...... 获取延迟加载实例-chicken2 Create Chicken Instance......
	 * <2> .当Egg bean 对 Chicken2 bean 依赖的时候输出： Create Chicken Instance......
	 * Create Chicken Instance...... Create Egg Instance...... 获取延迟加载实例-chicken2
	 *<P>
	 */
	@Test
	public void dependson_and_lazyTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "dependonApplicationContext.xml" });
			System.out.println("获取延迟加载实例-chicken2");
			Chicken c = (Chicken) ctx.getBean("chicken2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
