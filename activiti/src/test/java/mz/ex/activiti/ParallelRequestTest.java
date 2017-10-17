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

public class ParallelRequestTest {
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

		// Deploying means that the engine will parse the BPMN 2.0 xml to something executable and a new database record will be added for each process
		// definition included in the deployment
		deployment = repositoryService.createDeployment().addClasspathResource("ParallelRequest.bpmn20.xml").deploy();
	}

	@Test
	public void testProcessDefinitionQuery() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Long count = repositoryService.createProcessDefinitionQuery().processDefinitionKey("parallelRequest").count();
		assertEquals("1", count.toString());
	}

	@Test
	public void testFullVacationRequestFlow() throws InterruptedException {
		Map<String, Object> processVariables = new HashMap<>();

		// After deploying the process definition to the Activiti engine, we can start new process instances from it.
		// The process definition is the blueprint, while a process instance is a runtime execution of it.
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("parallelRequest", processVariables);
		long processInstancesCount = runtimeService.createProcessInstanceQuery().count();

		assertEquals(1, processInstancesCount);

		/* PROCESO INICIADO ------------------------------------------------------------------------------------------------------------------------- */

		Runnable r1 = new Runnable() {
			public void run() {
				List<Task> cashierTasks = taskService.createTaskQuery().taskCandidateGroup("cashier").list();
				Task receiveAmountTask = cashierTasks.get(0);

				Map<String, Object> receiveAmountTaskVariables = new HashMap<>();
				receiveAmountTaskVariables.put("amount", Double.valueOf(2500.25));
				synchronized (taskService) {
					taskService.complete(receiveAmountTask.getId(), receiveAmountTaskVariables);
				}
			}
		};
		Thread t1 = new Thread(r1);
		t1.start();

		/* PAGO RECIBIDO ------------------------------------------------------------------------------------------------------------------------- */

		Runnable r2 = new Runnable() {
			public void run() {
				List<Task> inventoryTasks = taskService.createTaskQuery().taskCandidateGroup("inventory").list();
				Task shipOrderTask = inventoryTasks.get(0);

				Map<String, Object> shipOrderTaskVariables = new HashMap<>();
				shipOrderTaskVariables.put("orderId", "12345-ABC");
				synchronized (taskService) {
					taskService.complete(shipOrderTask.getId(), shipOrderTaskVariables);
				}
			}
		};
		Thread t2 = new Thread(r2);
		t2.start();

		/* ORDEN ENVIADA ------------------------------------------------------------------------------------------------------------------------- */

		System.out.println("Esperando a finalizacion de tareas...");
		t1.join();
		t2.join();
		System.out.println("Tareas finalizadas");
		System.out.println("Archivando request...");

		List<Task> managerTasks = taskService.createTaskQuery().taskCandidateGroup("manager").list();
		Task archiveTask = managerTasks.get(0);

		System.out.println(archiveTask.getDescription());

		Map<String, Object> archiveTaskVariables = new HashMap<>();
		taskService.complete(archiveTask.getId(), archiveTaskVariables);

		/* REQUEST APROBADO -------------------------------------------------------------------------------------------------------------------------- */

		processInstancesCount = runtimeService.createProcessInstanceQuery().count();
		assertEquals(0, processInstancesCount);
	}

	@AfterClass
	public static void afterAll() {
		repositoryService.deleteDeployment(deployment.getId(), true);
	}
}
