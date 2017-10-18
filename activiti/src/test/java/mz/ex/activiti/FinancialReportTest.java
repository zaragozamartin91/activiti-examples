package mz.ex.activiti;

import static org.junit.Assert.*;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class FinancialReportTest {
	@Autowired
	ProcessEngine processEngine;

	static org.activiti.engine.repository.Deployment deployment;

	@Before
	public void before() {
		RepositoryService repositoryService = processEngine.getRepositoryService();

		deployment = repositoryService.createDeployment()
			.addClasspathResource("FinancialReportProcess.bpmn20.xml")
			.deploy();
	}

	@After
	public void after() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.deleteDeployment(deployment.getId(),true);
	}

	@Test
	@Deployment(resources = "FinancialReportProcess.bpmn20.xml")
	public void test() {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();

		// Start a process instance
		runtimeService.startProcessInstanceByKey("financialReport");

		long processInstancesCount = runtimeService.createProcessInstanceQuery().count();
		assertEquals(1, processInstancesCount);

		String userId = "foo";
		List<Task> fooTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		assertEquals(1, fooTasks.size());
		Task writeReportTask = fooTasks.get(0);

		// Al reclamar la tarea, otros usuarios del grupo al que foo pertenece no pueden tomar la tarea
		taskService.claim(writeReportTask.getId(), userId);
		assertEquals(0,taskService.createTaskQuery().taskCandidateGroup("accountancy").count());

		taskService.complete(writeReportTask.getId());

		Task verifyReportTask = taskService.createTaskQuery().taskCandidateUser("bar").list().get(0);
		System.out.println(verifyReportTask.getDescription());
		taskService.claim(verifyReportTask.getId(),"bar");
		taskService.complete(verifyReportTask.getId());

		processInstancesCount = runtimeService.createProcessInstanceQuery().count();
		assertEquals(0, processInstancesCount);
	}
}
