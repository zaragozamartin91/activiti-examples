package mz.ex.activiti.check;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class EditAccountServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Check check = (Check) execution.getVariable("check");
        /*		<activiti:formProperty id="account" name="Numero de cuenta" type="string" />
				<activiti:formProperty id="accountDigit" name="Digito verificador de Numero de cuenta" type="long" /> */
        Object account = execution.getVariable("account");
        Object accountDigit = execution.getVariable("accountDigit");

        check.setAccount((String) account);
        check.setAccountDigit((Integer) accountDigit);
    }
}
