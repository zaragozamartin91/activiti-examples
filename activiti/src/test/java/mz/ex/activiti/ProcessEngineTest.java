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
	static TaskService taskService;
	static RuntimeService runtimeService;
	static RepositoryService repositoryService;

	@BeforeClass
	public static void beforeAll() {
		applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
		processEngine = applicationContext.getBean(ProcessEngine.class);

		// Everything that is related to static data (such as process definitions) are accessed through the RepositoryService
		repositoryService = processEngine.getRepositoryService();

		taskService = processEngine.getTaskService();
		runtimeService = processEngine.getRuntimeService();

		// Deploying means that the engine will parse the BPMN 2.0 xml to something executable and a new database record will be added for each process definition included in the deployment
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
	public void testFullVacationRequestFlow() {
		String employeeName = "John";

		Map<String, Object> processVariables = new HashMap<>();
		processVariables.put("employeeName", employeeName);
		processVariables.put("numberOfDays", 4);
		processVariables.put("vacationMotivation", "I need a break!");

		// After deploying the process definition to the Activiti engine, we can start new process instances from it.
		// The process definition is the blueprint, while a process instance is a runtime execution of it.
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", processVariables);
		long processInstancesCount = runtimeService.createProcessInstanceQuery().count();

		assertEquals(1, processInstancesCount);

		/* PROCESO INICIADO ------------------------------------------------------------------------------------------------------------------------- */

		List<Task> managementTasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		Task approveRequestTask = managementTasks.get(0);

		System.out.println(approveRequestTask.getDescription());

		Map<String, Object> approveRequestTaskVariables = new HashMap<>();
		approveRequestTaskVariables.put("vacationApproved", "false");
		approveRequestTaskVariables.put("managerMotivation", "We have a tight deadline!");
		taskService.complete(approveRequestTask.getId(), approveRequestTaskVariables);

		/* REQUEST RECHAZADO ------------------------------------------------------------------------------------------------------------------------- */

		Task resendRequestTask = taskService.createTaskQuery()
				.taskAssignee(employeeName)
				.taskName("Adjust vacation request")
				.singleResult();
		assertNotNull(resendRequestTask);

		System.out.println(resendRequestTask.getDescription());
		/*
		 * <activiti:formProperty id="numberOfDays" name="Number of days" value="${numberOfDays}" type="long" required="true" /> <activiti:formProperty
		 * id="startDate" name="First day of holiday (dd-MM-yyy)" value="${startDate}" datePattern="dd-MM-yyyy hh:mm" type="date" required="true" />
		 * <activiti:formProperty id="vacationMotivation" name="Motivation" value="${vacationMotivation}" type="string" /> <activiti:formProperty
		 * id="resendRequest" name="Resend vacation request to manager?" type="enum" required="true">
		 * 
		 */
		Map<String, Object> resendRequestVariables = new HashMap<>();
		resendRequestVariables.put("numberOfDays", 3);
		resendRequestVariables.put("startDate", "21-03-1991 03:25");
		resendRequestVariables.put("vacationMotivation", "I really need a break");
		resendRequestVariables.put("resendRequest", "true");

		taskService.complete(resendRequestTask.getId(), resendRequestVariables);

		/* REQUEST REENVIADO ------------------------------------------------------------------------------------------------------------------------- */

		managementTasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		approveRequestTask = managementTasks.get(0);

		System.out.println(approveRequestTask.getDescription());

		approveRequestTaskVariables = new HashMap<>();
		approveRequestTaskVariables.put("vacationApproved", "true");
		approveRequestTaskVariables.put("managerMotivation", "mmm ok...");
		taskService.complete(approveRequestTask.getId(), approveRequestTaskVariables);

		/* REQUEST APROBADO -------------------------------------------------------------------------------------------------------------------------- */

		processInstancesCount = runtimeService.createProcessInstanceQuery().count();
		assertEquals(0, processInstancesCount);
	}

	@AfterClass
	public static void afterAll() {
		repositoryService.deleteDeployment(deployment.getId(), true);
	}
}
