/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jbpm.api.Configuration;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.JbpmException;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Task;

/**
 * base class for persistent jBPM tests.
 * 
 * This class exposes a lot of extra convenience methods for testing process
 * executions.
 * 
 * The ProcessEngine services will be initialized and available as member
 * fields.
 * 
 * This test assumes that each test will clean the DB itself and that no data is
 * in the DB tables when the test finishes.
 * 
 * During tearDown, a check will be done if all the DB tables are empty. If not,
 * that is logged with a F I X M E and the DB tables are cleaned.
 * 
 * @author Tom Baeyens
 * @author Heiko Braun
 */
public abstract class JbpmTestCase extends BaseJbpmTestCase {

	protected static ProcessEngine processEngine = null;

	protected static RepositoryService repositoryService;
	protected static ExecutionService executionService;
	protected static ManagementService managementService;
	protected static TaskService taskService;
	protected static HistoryService historyService;
	protected static IdentityService identityService;

	/**
	 * registered deployments. registered deployments will be deleted
	 * automatically in the tearDown. This is a convenience function as each
	 * test is expected to clean up the DB.
	 */
	protected List<String> registeredDeployments = new ArrayList<String>();

	protected void setUp() throws Exception {
		super.setUp();
		initialize();
	}

	protected synchronized void initialize() {
		if (processEngine == null) {
			// NDotTODO 哪里设置了[jbpm.test.cfg.type]属性 啊？【目前jbpm提供的类型有
			// null和spring-test】
			String jbpmTestCfgType = System.getProperty("jbpm.test.cfg.type");
			// 如果配置jbpmTestCfgType属性为
			// 1>. null，则放回org.jbpm.pvm.internal.cfg.JbpmConfiguration； 如果为
			// 2>. spring-test，返回org.jbpm.pvm.internal.cfg.SpringConfiguration；
			// 3>. 如果为其他的类型：会试图在当前线程的上下文中加载 对应的 实例（就是用类加载器加载对应的类，然后调用newInstance()创建一个实例），如果在
			// Jbpm4的Context中，没有配置对应的实例，则抛出异常throw new
			// JbpmException("couldn't instantiate configuration of type "
			// +className, e);
			Configuration configuration = new Configuration(jbpmTestCfgType);
            //如果没有配置该属性，会调用 默认的 jbpm.cfg.xml
			String jbpmTestCfgResource = System
					.getProperty("jbpm.test.cfg.resource");
			if (jbpmTestCfgResource != null) {
				configuration.setResource(jbpmTestCfgResource);
			}

			processEngine = configuration.buildProcessEngine();

			log.debug("using ProcessEngine "
					+ System.identityHashCode(processEngine));

			repositoryService = processEngine.get(RepositoryService.class);
			executionService = processEngine.getExecutionService();
			historyService = processEngine.getHistoryService();
			managementService = processEngine.getManagementService();
			taskService = processEngine.getTaskService();
			identityService = processEngine.getIdentityService();
		}
	}

	protected void tearDown() throws Exception {
		for (String deploymentId : registeredDeployments) {
			repositoryService.deleteDeploymentCascade(deploymentId);
		}

		String errorMsg = null;
		String recordsLeftMsg = Db.verifyClean(processEngine);
		if ((recordsLeftMsg != null) && (recordsLeftMsg.length() > 0)) {
			errorMsg = "database was not clean after test: " + recordsLeftMsg;
		}

		super.tearDown();

		if (errorMsg != null) {
			if (exception == null) {
				throw new JbpmException(errorMsg);
			} else {
				throw new JbpmException(errorMsg, exception);
			}
		}
	}

	// deployment helper methods
	// ////////////////////////////////////////////////

	/**
	 * deploys the process, keeps a reference to the deployment and
	 * automatically deletes the deployment in the tearDown
	 */
	public String deployJpdlXmlString(String jpdlXmlString) {
		String deploymentDbid = repositoryService.createDeployment()
				.addResourceFromString("xmlstring.jpdl.xml", jpdlXmlString)
				.deploy();

		registerDeployment(deploymentDbid);

		return deploymentDbid;
	}

	/** registered deployments will be deleted in the tearDown */
	protected void registerDeployment(String deploymentId) {
		registeredDeployments.add(deploymentId);
	}

	// task helper methods
	// //////////////////////////////////////////////////////

	public static void assertContainsTask(List<Task> taskList, String taskName) {
		if (getTask(taskList, taskName) == null) {
			fail("tasklist doesn't contain task '" + taskName + "': "
					+ taskList);
		}
	}

	public static void assertContainsTask(List<Task> taskList, String taskName,
			String assignee) {
		if (getTask(taskList, taskName, assignee) == null) {
			fail("tasklist doesn't contain task '" + taskName
					+ "' for assignee '" + assignee + "': " + taskList);
		}
	}

	public static Task getTask(List<Task> taskList, String taskName) {
		for (Task task : taskList) {
			if (taskName.equals(task.getName())) {
				return task;
			}
		}
		return null;
	}

	public static Task getTask(List<Task> taskList, String taskName,
			String assignee) {
		for (Task task : taskList) {
			if (taskName.equals(task.getName())) {
				if (assignee == null) {
					if (task.getAssignee() == null) {
						return task;
					}
				} else {
					if (assignee.equals(task.getAssignee())) {
						return task;
					}
				}
			}
		}
		return null;
	}

	// execution helper methods //////////////////////////////////////////

	public void assertExecutionEnded(String processInstanceId) {
		assertNull("Error: a process instance with id " + processInstanceId
				+ " was found", executionService
				.findProcessInstanceById(processInstanceId));
	}

	public void assertProcessInstanceEnded(String processInstanceId) {
		assertExecutionEnded(processInstanceId);
	}

	public void assertProcessInstanceEnded(ProcessInstance processInstance) {
		assertExecutionEnded(processInstance.getId());
	}

	public void assertActiveActivity(String activityName, String executionId) {
		Execution execution = executionService.findExecutionById(executionId);
		assertTrue(
				"The given execution (or any child execution) isn't in the activity '"
						+ activityName + "' (current activities : "
						+ listAllActiveActivites(executionId) + ")", execution
						.isActive(activityName));
	}

	public void assertExecutionInActivity(String executionId,
			String activityName) {
		assertTrue("The execution with id '" + executionId
				+ "' is not active in the activity '" + activityName + "'."
				+ "Current activitites are: "
				+ listAllActiveActivites(executionId), executionService
				.findExecutionById(executionId).isActive(activityName));
	}

	private String listAllActiveActivites(String executionId) {
		Execution execution = executionService.findExecutionById(executionId);
		Set<String> activeActivities = execution.findActiveActivityNames();
		StringBuilder result = new StringBuilder();
		for (String activeActivity : activeActivities) {
			result.append("'" + activeActivity + "', ");
		}
		result.setLength(result.length() - 2); // remove the last ', '
		return result.toString();
	}

}
