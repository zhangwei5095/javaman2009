package org.ndot.jbpm4;

import org.apache.log4j.Logger;
import org.jbpm.api.Configuration;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.ndot.jbpm4.log.Jbpm4Logger;

/**
 * 
 * 项目名称：jbpm4_study
 * 
 *<p>
 * 
 * 文件名称：Jbpm4Controller.java
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
public class Jbpm4Controller extends Jbpm4Logger {
	private String jbpmConfigurationXml;

	public void setJbpmConfigurationXml(String jbpmConfigurationXml) {
		this.jbpmConfigurationXml = jbpmConfigurationXml;
	}

	private Configuration jbpmConfiguration;
	private ProcessEngine processEngine;
	private RepositoryService repositoryService;
	private ExecutionService executionService;
	private TaskService taskService;
	private HistoryService historyService;
	private ManagementService managementService;

	public Jbpm4Controller() {
		try {
			if (null == this.jbpmConfiguration)
				this.jbpmConfiguration = new Configuration();
			if (null != this.jbpmConfigurationXml
					&& !"".equals(this.jbpmConfigurationXml)) {
				this.processEngine = this.jbpmConfiguration.setResource(
						jbpmConfigurationXml).buildProcessEngine();
			} else {
				this.processEngine = this.jbpmConfiguration
						.buildProcessEngine();
			}
			this.repositoryService = this.processEngine.getRepositoryService();
			this.executionService = this.processEngine.getExecutionService();
			this.taskService = this.processEngine.getTaskService();
			this.historyService = this.processEngine.getHistoryService();
			this.managementService = this.processEngine.getManagementService();
			this.setLog(Logger.getLogger(Jbpm4Controller.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setJbpmConfiguration(Configuration jbpmConfiguration) {
		this.jbpmConfiguration = jbpmConfiguration;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public ExecutionService getExecutionService() {
		return executionService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public static void main(String[] args) {
		try {

			Jbpm4Controller jbpm4Controller = new Jbpm4Controller();
			String pfile = "org/ndot/jbpm4/process/def/firsttset.jpdl.xml";
			String deploymentDbid = jbpm4Controller.getRepositoryService()
					.createDeployment().addResourceFromClasspath(pfile)
					.deploy();

			System.out.println("已经部署的流程id是：" + deploymentDbid);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
