package mz.ex.activiti;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProcessEngineTest {
	static ApplicationContext applicationContext;
	static ProcessEngine processEngine;
	static Deployment deployment;

	@BeforeClass
	public static void beforeAll() {
		applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
		processEngine = applicationContext.getBean(ProcessEngine.class);

		// RepositoryService permite manejar las definiciones de procesos y deploys
		RepositoryService repositoryService = processEngine.getRepositoryService();
		deployment = repositoryService.createDeployment().addClasspathResource("VacationRequest.bpmn20.xml").deploy();
	}

	@Test
	@Ignore
	public void givenXMLConfig_whenGetDefault_thenGotProcessEngine() {
		assertNotNull(processEngine);
		assertEquals("root", processEngine.getProcessEngineConfiguration().getJdbcUsername());
	}

	@Test
	public void testProcessDefinitionQuery() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Long count = repositoryService.createProcessDefinitionQuery().processDefinitionKey("vacationRequest").count();
		assertEquals("1", count.toString());
	}

	@Test
	public void givenDeployedProcess_whenStartProcessInstance_thenRunning() {
		String employeeName = "John";

		// deploy the process definition
		Map<String, Object> variables = new HashMap<>();
		variables.put("employeeName", employeeName);
		variables.put("numberOfDays", 4);
		variables.put("vacationMotivation", "I need a break!");

		// El runtime service sirve para obtener informacion sobre las instancias de proceso
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);
		Long count = runtimeService.createProcessInstanceQuery().count();

		assertEquals("1", count.toString());

		/* PROCESO INICIADO ------------------------------------------------------------------------------------------------ */

		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		Task task = tasks.get(0);

		Map<String, Object> taskVariables = new HashMap<>();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("managerMotivation", "We have a tight deadline!");
		taskService.complete(task.getId(), taskVariables);

		Task currentTask = taskService.createTaskQuery().taskAssignee(employeeName).singleResult();
		//Task currentTask = taskService.createTaskQuery().taskName("Adjust vacation request").singleResult();
		assertNotNull(currentTask);
	}

	@AfterClass
	public static void afterAll() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.deleteDeployment(deployment.getId(), true);
	}
}
