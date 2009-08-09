package org.ndot.jbpm4.junit;

import org.jbpm.api.ProcessInstance;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ndot.jbpm4.Jbpm4Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * 项目名称：jbpm4_study
 * 
 *<p>
 * 
 * 文件名称：Jbpm4BaseSpringTester.java
 * 
 *<P>
 * 
 * 功能结束：
 * 
 * 
 *<p>
 * 
 * 作 者：孙金城
 * 
 *<p>
 * 
 * 创建日期：2009-8-8
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

			System.out.println("已经部署的流程id是：" + deploymentDbid);
			String deploymentDbid2 = jbpm4Controller.getRepositoryService()
					.createDeployment().addResourceFromClasspath(pfile)
					.deploy();

			System.out.println("已经部署的流程id是：" + deploymentDbid2);
			ProcessInstance processInstance = jbpm4Controller
					.getExecutionService().startProcessInstanceByKey(
							deploymentDbid2);
			
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
