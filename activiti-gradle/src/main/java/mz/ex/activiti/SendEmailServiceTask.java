package mz.ex.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SendEmailServiceTask implements JavaDelegate {

	public void execute(DelegateExecution execution) {
		String employeeName = (String) execution.getVariable("employeeName");
		System.out.println(String.format("Sending %s's email", employeeName));
	}

}
