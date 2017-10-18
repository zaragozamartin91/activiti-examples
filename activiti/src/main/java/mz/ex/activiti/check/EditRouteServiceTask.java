package mz.ex.activiti.check;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class EditRouteServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Check check = (Check) execution.getVariable("check");
        /*
   				<activiti:formProperty id="bank" name="Codigo de Banco" type="string" />
				<activiti:formProperty id="branchOffice" name="Codigo de sucursal" type="string" />
				<activiti:formProperty id="postalCode" name="Codigo postal" type="string" />
				<activiti:formProperty id="routeDigit" name="Digito verificador de ruta de cobro" type="long" />
         */
        Object bank = execution.getVariable("bank");
        Object branchOffice = execution.getVariable("branchOffice");
        Object postalCode = execution.getVariable("postalCode");
        Object routeDigit = execution.getVariable("routeDigit");

        check.setBank((String) bank);
        check.setBranchOffice((String) branchOffice);
        check.setPostalCode((String) postalCode);
        check.setRouteDigit((Integer) routeDigit);

        System.out.println( "Entry check" + execution.getVariable("entryCheck") );
    }
}
