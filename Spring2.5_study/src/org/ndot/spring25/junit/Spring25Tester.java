package org.ndot.spring25.junit;

import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.ndot.spring25.People;
import org.ndot.spring25.annotation.Family;
import org.ndot.spring25.annotation.Member;
import org.ndot.spring25.annotation.qualifier.UseMyQualifier;
import org.ndot.spring25.annotation.scan.DBServices;
import org.ndot.spring25.autowire.AutowireMainBean;
import org.ndot.spring25.container.NDotSimpleXMLApplicationContext;
import org.ndot.spring25.inject.BeanA;
import org.ndot.spring25.inject.BeanB;
import org.ndot.spring25.inject.NullBean;
import org.ndot.spring25.inject.dependson.Chicken;
import org.ndot.spring25.instance.Canada;
import org.ndot.spring25.instance.Chinise;
import org.ndot.spring25.scop.UserManager;
import org.ndot.spring25.scop.xml.UserManagerXML;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
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

	/**
	 * 
	 * 功 能：自动装配测试
	 * 
	 *<P>
	 */
	@Test
	public void autowireTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "autowireApplicationContext.xml" });
			{
				// byName Autowire
				try {
					AutowireMainBean byNameAutowire = (AutowireMainBean) ctx
							.getBean("byNameAutowire");
					byNameAutowire.getAutowireBeanA().say(
							"I'am Autowired byName！");
				} catch (Exception e) {
					System.err
							.println("==============================================");
					System.err
							.println("【byName：AutowireMainBean 没有进行 依赖注入检查，AutowireMainBean注入失败......】");
					System.err
							.println("==============================================");
				}
			}

			{
				// byType AutowirebyConstructorAutowire
				try {
					AutowireMainBean byTypeAutowire = (AutowireMainBean) ctx
							.getBean("byTypeAutowire");
					byTypeAutowire.getAutowireBeanA().say(
							"I'am Autowired byType！");
				} catch (Exception e) {
					System.err
							.println("==============================================");
					System.err
							.println("【byType:AutowireMainBean 没有进行 依赖注入检查，AutowireMainBean注入失败......】");
					System.err
							.println("==============================================");
				}
			}

			{
				// byConstructorAutowire Autowire
				try {
					AutowireMainBean byConstructorAutowire = (AutowireMainBean) ctx
							.getBean("byConstructorAutowire");
					byConstructorAutowire.getAutowireBeanA().say(
							"I'am Autowired byConstructorAutowire！");
				} catch (Exception e) {
					System.err
							.println("==============================================");
					System.err
							.println("【byConstructorAutowire: AutowireMainBean 没有进行 依赖注入检查，AutowireMainBean注入失败......】");
					System.err
							.println("==============================================");
				}
			}

			{
				// byAutodetectAutowire Autowire
				try {
					AutowireMainBean byAutodetectAutowire = (AutowireMainBean) ctx
							.getBean("byAutodetectAutowire");
					byAutodetectAutowire.getAutowireBeanA().say(
							"I'am Autowired byAutodetectAutowire！");
				} catch (Exception e) {
					System.err
							.println("==============================================");
					System.err
							.println("【byAutodetectAutowire: AutowireMainBean 没有进行 依赖注入检查，AutowireMainBean注入失败......】");
					System.err
							.println("==============================================");
				}
			}
			// 关闭ctx
			((AbstractApplicationContext) ctx).registerShutdownHook();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 功 能：基于注解的注入测试
	 * 
	 *<P>
	 */
	@Test
	public void annotationTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "annotationApplicationContext.xml" });
			{
				Family f = (Family) ctx.getBean("family");

				System.out.println("=====================");
				System.out.println("家庭名称：" + f.getFname());
				System.out.println("=====================");
				System.out.println("家庭成员：");
				Map<String, Member> members = f.getMembers();
				Iterator it = members.keySet().iterator();
				while (it.hasNext()) {
					String mrole = (String) it.next();
					System.out.println("家庭角色: " + mrole);
					Member m = (Member) members.get(mrole);
					System.out.println("性别: " + m.getMsex());
				}
				System.out.println("=====================");
				System.out.println("家庭地址：" + f.getAddress().getCountry() + ":"
						+ f.getAddress().getCity());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：基于注解的【自定义Qualifier】注入测试
	 * 
	 *<P>
	 */
	@Test
	public void qualifierAnnotationTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "qulifierAnnotationApplicationContext.xml" });
			UseMyQualifier u = (UseMyQualifier) ctx.getBean("useMyQualifier");
			u.getBean1().say();
			u.getBean2().say();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：基于 注解和自动扫描 注入测试
	 * 
	 *<P>
	 */
	@Test
	public void scanAnnotationTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "scanAnnotationApplicationContext.xml" });
			DBServices ds = (DBServices) ctx.getBean("dBservices");
			ds.getAdao().doSave();
			ds.getBdao().doSave();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：基于 Bean 作用域 xml配置方式 测试
	 * 
	 *<P>
	 */
	@Test
	public void scopXmlTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "scopXmlApplicationContext.xml" });
			{
				// seter 注入 prototype类型bean:
				System.out.println("seter 注入 prototype类型bean: ");
				UserManagerXML um = (UserManagerXML) ctx.getBean("userManager");
				int u1hashcode = um.getUserInfo().hashCode();
				System.out.println("u1hashcode=" + u1hashcode);
				UserManagerXML um2 = (UserManagerXML) ctx
						.getBean("userManager");
				int u2hashcode = um2.getUserInfo().hashCode();
				System.out.println("u2hashcode=" + u2hashcode);
				System.out.println("u1hashcode==u2hashcode) = "
						+ (u1hashcode == u2hashcode));
				if (u1hashcode == u2hashcode) {
					System.out
							.println("两个UserInfo的hashcode 相同，说明注入的同一个userInfo实例，与期望不同，没有解决");
					System.out.println("Singleton beans和prototype-bean的依赖需求");
				} else {
					System.out
							.println("两个UserInfo的hashcode 不同，说明注入了不同的userInfo实例，与期望相符，解决了");
					System.out.println("Singleton beans和prototype-bean的依赖需求");

				}
				System.out.println("==================================");
				System.out.println("um.hashCode()==um2.hashCode()="
						+ (um.hashCode() == um2.hashCode()));
				System.out
						.println("UserManagerXML 是Singleton beans，UserInfoXML 是prototype-bean");
			}
			{
				// LookUp 方法注入 prototype类型bean:
				System.out.println("=====================================");
				System.out.println("LookUp 方法注入 prototype类型bean:");
				UserManagerXML um = (UserManagerXML) ctx
						.getBean("userManagerLookup");
				um.setUserInfo(um.getNewUserInfo());
				int u1hashcode = um.getUserInfo().hashCode();
				System.out.println("u1hashcode=" + u1hashcode);
				UserManagerXML um2 = (UserManagerXML) ctx
						.getBean("userManagerLookup");
				um2.setUserInfo(um2.getNewUserInfo());
				int u2hashcode = um2.getUserInfo().hashCode();
				System.out.println("u2hashcode=" + u2hashcode);
				System.out.println("u1hashcode==u2hashcode) = "
						+ (u1hashcode == u2hashcode));
				if (u1hashcode == u2hashcode) {
					System.out
							.println("两个UserInfo的hashcode 相同，说明注入的同一个userInfo实例，与期望不同，没有解决");
					System.out.println("Singleton beans和prototype-bean的依赖需求");
				} else {
					System.out
							.println("两个UserInfo的hashcode 不同，说明注入了不同的userInfo实例，与期望相符，解决了");
					System.out.println("Singleton beans和prototype-bean的依赖需求");

				}
				System.out.println("==================================");
				System.out.println("um.hashCode()==um2.hashCode()="
						+ (um.hashCode() == um2.hashCode()));
				System.out
						.println("UserManagerXML 是Singleton beans，UserInfoXML 是prototype-bean");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：基于 Bean 作用域 注释方式 测试
	 * 
	 *<P>
	 */
	@Test
	public void scopTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "scopApplicationContext.xml" });
			{
				// seter 注入 prototype类型bean:
				System.out.println("seter 注入 prototype类型bean: ");
				UserManager um = (UserManager) ctx.getBean("userManager");
				int u1hashcode = um.getUserInfo().hashCode();
				System.out.println("u1hashcode=" + u1hashcode);
				UserManager um2 = (UserManager) ctx.getBean("userManager");
				int u2hashcode = um2.getUserInfo().hashCode();
				System.out.println("u2hashcode=" + u2hashcode);
				System.out.println("u1hashcode==u2hashcode) = "
						+ (u1hashcode == u2hashcode));
				if (u1hashcode == u2hashcode) {
					System.out
							.println("两个UserInfo的hashcode 相同，说明注入的同一个userInfo实例，与期望不同，没有解决");
					System.out.println("Singleton beans和prototype-bean的依赖需求");
				} else {
					System.out
							.println("两个UserInfo的hashcode 不同，说明注入了不同的userInfo实例，与期望相符，解决了");
					System.out.println("Singleton beans和prototype-bean的依赖需求");

				}
				System.out.println("==================================");
				System.out.println("um.hashCode()==um2.hashCode()="
						+ (um.hashCode() == um2.hashCode()));
				System.out.println("UserManager 是Singleton beans");
			}

			{

				// LookUp 方法注入 prototype类型bean:
				System.out.println("=====================================");
				System.out.println("LookUp 方法注入 prototype类型bean:");
				UserManager um = (UserManager) ctx.getBean("userManager");
				um.setUserInfo(um.getNewUserInfo());
				int u1hashcode = um.getUserInfo().hashCode();
				System.out.println("u1hashcode=" + u1hashcode);
				UserManager um2 = (UserManager) ctx.getBean("userManager");
				um2.setUserInfo(um2.getNewUserInfo());
				int u2hashcode = um2.getUserInfo().hashCode();
				System.out.println("u2hashcode=" + u2hashcode);
				System.out.println("u1hashcode==u2hashcode) = "
						+ (u1hashcode == u2hashcode));
				if (u1hashcode == u2hashcode) {
					System.out
							.println("两个UserInfo的hashcode 相同，说明注入的同一个userInfo实例，与期望不同，没有解决");
					System.out.println("Singleton beans和prototype-bean的依赖需求");
				} else {
					System.out
							.println("两个UserInfo的hashcode 不同，说明注入了不同的userInfo实例，与期望相符，解决了");
					System.out.println("Singleton beans和prototype-bean的依赖需求");

				}
				System.out.println("==================================");
				System.out.println("um.hashCode()==um2.hashCode()="
						+ (um.hashCode() == um2.hashCode()));
				System.out
						.println("UserManager 是Singleton beans，UserInfo 是prototype-bean");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
