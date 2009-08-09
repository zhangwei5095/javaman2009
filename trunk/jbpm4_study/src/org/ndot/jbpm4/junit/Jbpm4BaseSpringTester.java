package org.ndot.jbpm4.junit;

import org.jbpm.api.ProcessInstance;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ndot.jbpm4.Jbpm4Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * ��Ŀ���ƣ�jbpm4_study
 * 
 *<p>
 * 
 * �ļ����ƣ�Jbpm4BaseSpringTester.java
 * 
 *<P>
 * 
 * ���ܽ�����
 * 
 * 
 *<p>
 * 
 * �� �ߣ�����
 * 
 *<p>
 * 
 * �������ڣ�2009-8-8
 * 
 */

public class Jbpm4BaseSpringTester {
	public static ApplicationContext ctx;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext.xml", "actionApplicationContext.xml" });
	}

	@Test
	public void testDeployProcess() {
		try {
			Jbpm4Controller jbpm4Controller = (Jbpm4Controller) ctx
					.getBean("jbmpController");
			String pfile = "org/ndot/jbpm4/process/def/firsttset.jpdl.xml";
			String deploymentDbid = jbpm4Controller.getRepositoryService()
					.createDeployment().addResourceFromClasspath(pfile)
					.deploy();

			System.out.println("�Ѿ����������id�ǣ�" + deploymentDbid);
			String deploymentDbid2 = jbpm4Controller.getRepositoryService()
					.createDeployment().addResourceFromClasspath(pfile)
					.deploy();

			System.out.println("�Ѿ����������id�ǣ�" + deploymentDbid2);
			ProcessInstance processInstance = jbpm4Controller
					.getExecutionService().startProcessInstanceByKey(
							deploymentDbid2);
			
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
