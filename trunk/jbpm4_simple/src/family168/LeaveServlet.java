package family168;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.jbpm.api.Configuration;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

/**
 * @author kayzhan
 * 
 * ��ʾ����www.family168.com��jBPM4��Ƶ�̳̻���Ӧ��ϵ�е�����
 */
public class LeaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProcessEngine processEngine = null;
	private RepositoryService repositoryService = null;
	private ExecutionService executionService = null;
	private ProcessInstance processInstance = null;
	private TaskService taskService = null;
	private Task task = null;
	private Execution execution = null;
	private String url = null;
	private List list = null;
	private List<Task> taskList = null;
	private Map<String,Object> map = null;
	
	/* ��ʼ��ProcessEngine. */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		processEngine = new Configuration().buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		executionService = processEngine.getExecutionService();
		taskService = processEngine.getTaskService();
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String family168 = request.getParameter("family168");
		
		if (family168.equals("deploy")) {
			try {
				deploy(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list = getLatestProcessDefinition(request,response);
			request.setAttribute("process", list);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} else if (family168.equals("leave")) {
			url = leave(request,response);
			request.getRequestDispatcher(url).forward(request, response);
		} else if (family168.equals("query")) {
			list = getProcessInstanceById(request,response);
			request.setAttribute("pi", list);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} else if (family168.equals("apply")) {
			apply(request,response);
			list = getProcessInstanceById(request,response);
			request.setAttribute("pi", list);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} else if (family168.equals("getTask")) {
			list = getTask(request,response);
			request.setAttribute("taskList", list);
			request.getRequestDispatcher("/check.jsp").forward(request, response);
		} else if (family168.equals("view")) {
			map = view(request,response);
			request.setAttribute("map", map);
			request.getRequestDispatcher("/view.jsp").forward(request, response);
		} else if (family168.equals("confirm")) {
			confirm(request,response);
			list = getTask(request,response);
			request.setAttribute("taskList", list);
			request.getRequestDispatcher("/check.jsp").forward(request, response);
		} else if (family168.equals("dissent")) {
			dissent(request,response);
			list = getTask(request,response);
			request.setAttribute("taskList", list);
			request.getRequestDispatcher("/check.jsp").forward(request, response);
		} else if (family168.equals("reject")) {
			reject(request,response);
			list = getTask(request,response);
			request.setAttribute("taskList", list);
			request.getRequestDispatcher("/check.jsp").forward(request, response);
		} else if (family168.equals("reApply")) {
			map = reApply(request,response);
			request.setAttribute("map", map);
			url = (String) map.get("url");
			request.getRequestDispatcher(url).forward(request, response);
		}
		
        /*try {
            Class clz = this.getClass();
            Method method = clz.getDeclaredMethod(action,
                    HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        */
	}
	
	protected void deploy(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		String temp = getServletContext().getRealPath("/temp");
		String upload = getServletContext().getRealPath("/upload");
		DiskFileUpload diskFileUpload = new DiskFileUpload();
		diskFileUpload.setFileSizeMax(1 * 1024 * 1024);
		diskFileUpload.setSizeThreshold(4096);
		diskFileUpload.setRepositoryPath(temp);
		
		List fileItems = diskFileUpload.parseRequest(request);
		Iterator iter = fileItems.iterator();
		
		if (iter.hasNext()) {
			FileItem item = (FileItem)iter.next();
			
			if (!item.isFormField()) {
				String name = item.getName();
				long size = item.getSize();
				System.out.println("name: " + name);
				if (name != null && !name.equals("") && size >0) {
					repositoryService.createDeployment()
								.addResourcesFromZipInputStream(new ZipInputStream(item.getInputStream())).deploy();
				}
			}
		}
		
	}
	
	protected String leave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processInstance = executionService.startProcessInstanceByKey("leave");
		//String pid = processInstance.getId();
		if (processInstance.isActive("��д��ٵ�")) {
			url = "/apply.jsp";
		}
		return url;
	}
	
	protected Map<String,Object> apply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String,Object> variables = new HashMap<String,Object>();
		String name = request.getParameter("applyName");
		String time = request.getParameter("applyTime");
		String leaveDay = request.getParameter("leaveDay");
		String content = request.getParameter("content");
		String position = request.getParameter("position");

		variables.put("name", name);
		variables.put("time", time);
		variables.put("leaveDay", leaveDay);
		variables.put("content", content);
		variables.put("position", position);
		if (position.trim().equals("����")) {
			variables.put("manager", "��");
		} else {
			variables.put("manager", "��");
		}
		taskList = taskService.findPersonalTasks("Kayzhan");
		System.out.println("Kayzhan taskList: " + taskList);
		task = taskList.get(0);
		taskService.setVariables(task.getId(), variables);
		taskService.completeTask(task.getId());
		execution = executionService.findExecutionById(task.getExecutionId());
		System.out.println("isActive:  " + execution.getProcessInstance().isActive("�ϰ�����"));
		return variables;
	}
	
	protected List<Task> getTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		taskList = taskService.findPersonalTasks(name);
		return taskList;
	}
	
	protected Map<String,Object> view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter("id");
		Set<String> set = taskService.getVariableNames(taskId);
		map = taskService.getVariables(taskId, set);
		
		return map;
	}
	
	protected void confirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter("id");
		task = taskService.getTask(taskId);
		execution = executionService.findExecutionById(task.getExecutionId());
		if (execution.getProcessInstance().isActive("�ϰ�����")) {
			taskService.completeTask(taskId, "�ϰ���׼");
		} else if (execution.getProcessInstance().isActive("�������")) {
			String variable = (String) taskService.getVariable(taskId, "leaveDay");
			if (Integer.valueOf(variable) > 3) {
				taskService.completeTask(taskId, "�������>3");
			} else {
				taskService.completeTask(taskId, "������׼");
			}
		}
	}
	
	protected void dissent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter("id");
		task = taskService.getTask(taskId);
		execution = executionService.findExecutionById(task.getExecutionId());
		if (execution.getProcessInstance().isActive("�ϰ�����")) {
			taskService.completeTask(taskId, "�ϰ岻��׼");
		} else if (execution.getProcessInstance().isActive("�������")) {
			taskService.completeTask(taskId, "������׼");
		}
	}
	
	protected void reject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter("id");
		task = taskService.getTask(taskId);
		Set<String> set = taskService.getVariableNames(taskId);
		map = taskService.getVariables(taskId, set);
		map.put("reject", task.getAssignee());
		taskService.setVariables(task.getId(), map);
		execution = executionService.findExecutionById(task.getExecutionId());
		if (execution.getProcessInstance().isActive("�ϰ�����")) {
			taskService.completeTask(taskId, "�ϰ岵��");
		} else if (execution.getProcessInstance().isActive("�������")) {
			taskService.completeTask(taskId, "������");
		}
		
		System.out.println("���ػ�� " + execution.getProcessInstance().isActive("��д��ٵ�"));
	
	}
	
	protected Map<String,Object> reApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter("id");
		task = taskService.getTask(taskId);
		execution = executionService.findExecutionById(task.getExecutionId());
		if (execution.getProcessInstance().isActive("��д��ٵ�")) {
			url = "reApply.jsp";
		} 
		Set<String> set = taskService.getVariableNames(taskId);
		map = taskService.getVariables(taskId, set);
		map.put("url", url);
		return map;
	}
	
	protected List<ProcessDefinition> getLatestProcessDefinition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
						.orderAsc(ProcessDefinitionQuery.PROPERTY_NAME).list();
		Map<String,ProcessDefinition> map = new LinkedHashMap<String,ProcessDefinition>();
		for (ProcessDefinition pd : processDefinitions) {
			String key = pd.getKey();
			ProcessDefinition definition = map.get(key);
			if ((definition == null) || (definition.getVersion() < pd.getVersion())) {
				map.put(key, pd);
			}
		}
		return new ArrayList(map.values());
	}
	
	protected List<ProcessInstance> getProcessInstanceById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pdId = request.getParameter("id");
		return executionService.createProcessInstanceQuery()
        .processDefinitionId(pdId).list();
	}
	
	
}
