package mz.ex.activiti;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;


/**
 * Ejemplo de tests de activiti usando junit4.
 * 
 * @author martin.zaragoza
 *
 */
public class VacationRequestJunit4Test {
	/* Este tipo de tests cuentan con que exista el archivo activiti.cfg.xml en el classpath */
	
	/* A traves de esta Rule es posible obtener los servicios de activiti */
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	/* los tests anotados con Deployment garantizan que se haga el deploy y eliminacion de un proceso al inicio y final del
	 * test respectivamente. */
	@Test
	@Deployment(resources = "VacationRequest.bpmn20.xml")
	public void ruleUsageExample() {
	    RuntimeService runtimeService = activitiRule.getRuntimeService();
	    TaskService taskService = activitiRule.getTaskService();
		String employeeName = "John";

		Map<String, Object> processVariables = new HashMap<>();
		processVariables.put("employeeName", employeeName);
		processVariables.put("numberOfDays", 4);
		processVariables.put("vacationMotivation", "I need a break!");

		// After deploying the process definition to the Activiti engine, we can start new process instances from it.
		// The process definition is the blueprint, while a process instance is a runtime execution of it.
		runtimeService.startProcessInstanceByKey("vacationRequest", processVariables);
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
}
