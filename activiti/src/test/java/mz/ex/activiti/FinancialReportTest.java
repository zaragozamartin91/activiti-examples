package mz.ex.activiti;

import static org.junit.Assert.*;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

public class FinancialReportTest {
	/* A traves de esta Rule es posible obtener los servicios de activiti */
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	@Deployment(resources = "FinancialReportProcess.bpmn20.xml")
	public void test() {
		RuntimeService runtimeService = activitiRule.getRuntimeService();

		runtimeService.startProcessInstanceByKey("financialReport");
		
		
	}
}
