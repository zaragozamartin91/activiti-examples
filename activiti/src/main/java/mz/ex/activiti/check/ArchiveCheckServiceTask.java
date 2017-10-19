package mz.ex.activiti.check;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ArchiveCheckServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Check check = (Check) execution.getVariable("check");

        Object account = execution.getVariable("account");
        Object accountDigit = execution.getVariable("accountDigit");

        check.setAccount((String) account);
        check.setAccountDigit((Integer) accountDigit);

        Object bank = execution.getVariable("bank");
        Object branchOffice = execution.getVariable("branchOffice");
        Object postalCode = execution.getVariable("postalCode");
        Object routeDigit = execution.getVariable("routeDigit");

        check.setBank((String) bank);
        check.setBranchOffice((String) branchOffice);
        check.setPostalCode((String) postalCode);
        check.setRouteDigit((Integer) routeDigit);

        System.out.println(execution.getVariable("editRoute"));

        System.out.println("Archivando cheque: " + check);
    }
}
