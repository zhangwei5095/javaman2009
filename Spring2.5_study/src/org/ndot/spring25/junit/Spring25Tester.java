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
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� Spring25Tester.java
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
 * ����ʱ��: 2009-8-20
 * 
 */
public class Spring25Tester {
	/**
	 * �򵥵Ĳ��������Ĵ�����bean �Ļ�ȡ
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
	 * �� �ܣ������Զ����IOC��������bean
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
	 * �� �ܣ�����ʵ����bean�����ַ�ʽ
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
	 * �� �ܣ���������ע�빦��
	 * 
	 *<P>
	 */
	@Test
	public void beanInjectTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "injectApplicationContext.xml" });
			{
				// ע�뼯������
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
				// ���Ϻϲ�
				BeanB b1 = (BeanB) ctx.getBean("BeanBInject1");
				System.out.println("b1.getListp().size()="
						+ b1.getListp().size());
				BeanB b2 = (BeanB) ctx.getBean("BeanBInject2");
				System.out.println("b2.getListp().size()="
						+ b2.getListp().size());
			}
			{
				// ǿ������ת��
				BeanB b3 = (BeanB) ctx.getBean("BeanBInject3");
				try {
					Float f1 = (Float) b3.getMapp().get("aaa");
					Float f2 = (Float) b3.getMapp().get("bbb");
					System.out.println(f1 + f2);
				} catch (Exception e) {
					System.err.println("========================");
					System.err.println("û��ʹ��Spring��ǿ������װ������,��ֵ�����쳣......");
					System.err.println("========================");
				}
				System.out.println("ʹ��Spring��ǿ������װ������,��ֵ����ɹ���������£�");
				System.out.println((b3.getFloatMap().get("aaa"))
						+ (b3.getFloatMap().get("bbb")));
			}
			{
				// Null ������
				NullBean nb = (NullBean) ctx.getBean("nullbean");
				System.out.println("nb.getNullValue()=" + nb.getNullValue());
				System.out.println("nb.getEmptyStr()+NDot =" + nb.getEmptyStr()
						+ "NDot");
			}
			{
				// ����ע��lookup
				System.out.println(((BeanA) ctx.getBean("beana"))
						.injectBeanBInstance().hashCode());
				System.out.println(((BeanA) ctx.getBean("beana"))
						.injectBeanBInstance().hashCode());

				System.out.println(((BeanA) ctx.getBean("beana"))
						.injectBeanBInstance().hashCode());


			}
			System.out.println("====��ϲ����ȫ�����Գɹ�����)=====");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * �� �ܣ������ӳټ��غ�����ʵ���� ����Ĳ�����Ҫ�� ����ApplicationContext����ʵ��
	 * ��ʱ������bean��ʵ�������̣�Ҫ�۲���־��ʵ������˳��
	 * 
	 * <1> .��Egg bean û�ж� Chicken2 bean ������ʱ������� Create Chicken Instance......
	 * Create Egg Instance...... ��ȡ�ӳټ���ʵ��-chicken2 Create Chicken Instance......
	 * <2> .��Egg bean �� Chicken2 bean ������ʱ������� Create Chicken Instance......
	 * Create Chicken Instance...... Create Egg Instance...... ��ȡ�ӳټ���ʵ��-chicken2
	 *<P>
	 */
	@Test
	public void dependson_and_lazyTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "dependonApplicationContext.xml" });
			System.out.println("��ȡ�ӳټ���ʵ��-chicken2");
			Chicken c = (Chicken) ctx.getBean("chicken2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
