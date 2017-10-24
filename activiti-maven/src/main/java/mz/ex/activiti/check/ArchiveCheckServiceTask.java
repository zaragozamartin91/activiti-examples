package mz.ex.activiti.check;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ArchiveCheckServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Check check = (Check) execution.getVariable("check");

        Object account = execution.getVariable("account");
        Object accountDigit = execution.getVariable("accountDigit");

        if (account != null) check.setAccount((String) account);
        if (accountDigit != null) check.setAccountDigit((Integer) accountDigit);

        Object bank = execution.getVariable("bank");
        Object branchOffice = execution.getVariable("branchOffice");
        Object postalCode = execution.getVariable("postalCode");
        Object routeDigit = execution.getVariable("routeDigit");

        if(bank != null) check.setBank((String) bank);
        if(branchOffice != null) check.setBranchOffice((String) branchOffice);
        if(postalCode != null) check.setPostalCode((String) postalCode);
        if(routeDigit != null) check.setRouteDigit((Integer) routeDigit);

        System.out.println(execution.getVariable("editRoute"));

        System.out.println("Cheque modificado: " + check);
    }
}
