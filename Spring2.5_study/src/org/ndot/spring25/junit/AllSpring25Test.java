package org.ndot.spring25.junit;

import java.util.Iterator;
import java.util.List;
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
import org.ndot.spring25.mutiDataSource.TestService;
import org.ndot.spring25.scop.UserManager;
import org.ndot.spring25.scop.xml.UserManagerXML;
import org.ndot.spring25.transaction.pojo.Usertab;
import org.ndot.spring25.transaction.services.DBService;
import org.ndot.spring25.transaction.services.DBServiceImp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
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
 * �ļ����� AllSpring25Test.java
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
 * ����ʱ��: 2008-9-21
 * 
 */
public class AllSpring25Test {

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
				BeanB b1 = (BeanB) ctx.getBean("BeanBInject1");
				System.out.println("b1.getListp().size()="
						+ b1.getListp().size());
				BeanB b2 = (BeanB) ctx.getBean("BeanBInject2");
				System.out.println("b2.getListp().size()="
						+ b2.getListp().size());
			}
			{
				BeanB b3 = (BeanB) ctx.getBean("BeanBInject3");
				try {
					Float f1 = (Float) b3.getMapp().get("aaa");
					Float f2 = (Float) b3.getMapp().get("bbb");
					System.out.println(f1 + f2);
				} catch (Exception e) {
					System.err.println("========================");
					System.err.println("");
					System.err.println("========================");
				}
				System.out.println("");
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
			System.out.println("=========");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void dependson_and_lazyTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "dependonApplicationContext.xml" });
			System.out.println("-chicken2");
			Chicken c = (Chicken) ctx.getBean("chicken2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
							"I'am Autowired byNam");
				} catch (Exception e) {
					System.err
							.println("==============================================");
					System.err.println("");
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
							"I'am Autowired byType");
				} catch (Exception e) {
					System.err
							.println("==============================================");
					System.err.println("");
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
							"I'am Autowired byConstructorAutowire");
				} catch (Exception e) {
					System.err
							.println("==============================================");
					System.err.println("");
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
							"I'am Autowired byAutodetectAutowire��");
				} catch (Exception e) {
					System.err
							.println("==============================================");
					System.err.println("");
					System.err
							.println("==============================================");
				}
			}
			// �ر�ctx
			((AbstractApplicationContext) ctx).registerShutdownHook();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void annotationTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "annotationApplicationContext.xml" });
			{
				Family f = (Family) ctx.getBean("family");

				System.out.println("=====================");
				System.out.println("" + f.getFname());
				System.out.println("=====================");
				System.out.println("");
				Map<String, Member> members = f.getMembers();
				Iterator it = members.keySet().iterator();
				while (it.hasNext()) {
					String mrole = (String) it.next();
					System.out.println(" " + mrole);
					Member m = (Member) members.get(mrole);
					System.out.println(" " + m.getMsex());
				}
				System.out.println("=====================");
				System.out.println("" + f.getAddress().getCountry() + ":"
						+ f.getAddress().getCity());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	@Test
	public void scopXmlTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "scopXmlApplicationContext.xml" });
			{
				System.out.println("seter bean: ");
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
					System.out.println("");
					System.out.println("");
				} else {
					System.out.println("");
					System.out.println("");

				}
				System.out.println("==================================");
				System.out.println("um.hashCode()==um2.hashCode()="
						+ (um.hashCode() == um2.hashCode()));
				System.out.println("");
			}
			{
				// LookUp ����ע�� prototype����bean:
				System.out.println("=====================================");
				System.out.println("");
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
					System.out.println("");
					System.out.println("");
				} else {
					System.out.println("");
					System.out.println("");

				}
				System.out.println("==================================");
				System.out.println("um.hashCode()==um2.hashCode()="
						+ (um.hashCode() == um2.hashCode()));
				System.out.println("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void scopTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "scopApplicationContext.xml" });
			{
				// seter ע�� prototype����bean:
				System.out.println("");
				UserManager um = (UserManager) ctx.getBean("userManager");
				int u1hashcode = um.getUserInfo().hashCode();
				System.out.println("u1hashcode=" + u1hashcode);
				UserManager um2 = (UserManager) ctx.getBean("userManager");
				int u2hashcode = um2.getUserInfo().hashCode();
				System.out.println("u2hashcode=" + u2hashcode);
				System.out.println("u1hashcode==u2hashcode) = "
						+ (u1hashcode == u2hashcode));
				if (u1hashcode == u2hashcode) {
					System.out.println("");
					System.out.println("");
				} else {
					System.out.println("");
					System.out.println("");

				}
				System.out.println("==================================");
				System.out.println("um.hashCode()==um2.hashCode()="
						+ (um.hashCode() == um2.hashCode()));
				System.out.println("UserManager Singleton beans");
			}

			{

				// LookUp ����ע�� prototype����bean:
				System.out.println("=====================================");
				System.out.println("LookUp  prototypebean:");
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
					System.out.println("");
					System.out.println("");
				} else {
					System.out.println("");
					System.out.println("");

				}
				System.out.println("==================================");
				System.out.println("um.hashCode()==um2.hashCode()="
						+ (um.hashCode() == um2.hashCode()));
				System.out.println("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void quartzTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "applicationContext-quartz.xml" });
			Thread.sleep(60000);
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TransactionTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "transactionalApplicationContext.xml" });
			DBService dbs = (DBServiceImp) ctx.getBean("DBServiceImp");
			Usertab user = new Usertab();
			user.setId("666");
			user.setName("NDot");
			dbs.save(user);

			List<Usertab> users = dbs.findAll();
			for (Usertab use : users) {
				System.out.println("UserName : " + use.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void MutiDataSource() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "applicationContext_mutiDataSource.xml" });

			TestService ts = (TestService) ctx.getBean("testService");
			ts.insert(
							"insert into authors values(777,'ipssman@163.com','fdsf')",
							"Yxh");

			ts.insert("insert into NDot values('PanPan')", "Admin");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
