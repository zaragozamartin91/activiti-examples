package mz.ex.activiti.check;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ArchiveCheckServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Check check = (Check) execution.getVariable("check");
        System.out.println("Archivando cheque: " + check);
    }
}
