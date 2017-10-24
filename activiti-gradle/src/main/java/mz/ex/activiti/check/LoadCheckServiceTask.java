package mz.ex.activiti.check;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class LoadCheckServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Object checkId = execution.getVariable("checkId");
        CheckRepository checkRepository = CheckRepository.getInstance();
        Check check = checkRepository.getCheck(checkId.toString());
        execution.setVariable("check",check);
    }
}
