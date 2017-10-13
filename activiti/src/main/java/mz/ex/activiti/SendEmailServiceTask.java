package mz.ex.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SendEmailServiceTask implements JavaDelegate {

	public void execute(DelegateExecution execution) {
		System.out.println("SENDING EMAIL!");
	}

}
