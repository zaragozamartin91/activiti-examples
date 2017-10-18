package mz.ex.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class EditCheckTest {
	@Autowired
	ProcessEngine processEngine;

	static org.activiti.engine.repository.Deployment deployment;

	@Before
	public void before() {
		RepositoryService repositoryService = processEngine.getRepositoryService();

		deployment = repositoryService.createDeployment()
			.addClasspathResource("EditCheck.bpmn20.xml")
			.deploy();
	}

	@After
	public void after() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.deleteDeployment(deployment.getId(),true);
	}

	@Test
	public void test() {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();

		// Start a process instance
		Map<String,Object> variables = new HashMap<>();
		variables.put("checkId","macro-1234");
		runtimeService.startProcessInstanceByKey("editCheck",variables);

		long processInstancesCount = runtimeService.createProcessInstanceQuery().count();
		assertEquals(1, processInstancesCount);

		/*
		* 		<activiti:formProperty id="bank" name="Codigo de Banco" type="string" />
				<activiti:formProperty id="branchOffice" name="Codigo de sucursal" type="string" />
				<activiti:formProperty id="postalCode" name="Codigo postal" type="string" />
				<activiti:formProperty id="routeDigit" name="Digito verificador de ruta de cobro" type="long" />
		* */
		Task editRouteTask = taskService.createTaskQuery().taskCandidateUser("foo").singleResult();
		assertNotNull(editRouteTask);

		Map<String,Object> editRouteVariables = new HashMap<>();
		editRouteVariables.put("bank","macro");
		editRouteVariables.put("branchOffice","cordoba");
		editRouteVariables.put("postalCode","1640");
		editRouteVariables.put("routeDigit",1);

		taskService.complete(editRouteTask.getId(),editRouteVariables);

		/*
				<activiti:formProperty id="account" name="Numero de cuenta" type="string" />
				<activiti:formProperty id="accountDigit" name="Digito verificador de Numero de cuenta" type="long" />
		*/

		Task editAccountTask = taskService.createTaskQuery().taskCandidateUser("bar").singleResult();
		assertNotNull(editAccountTask);

		Map<String,Object> editAccountVariables = new HashMap<>();
		editAccountVariables.put("account","123456789");
		editAccountVariables.put("accountDigit",2);

		taskService.complete(editAccountTask.getId(),editAccountVariables);

		Task archiveCheckTask = taskService.createTaskQuery().taskCandidateUser("zee").singleResult();
		taskService.complete(archiveCheckTask.getId());
	}
}
