package mz.ex.activiti.check;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class DummyServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("DOING NOTHING");
    }
}
