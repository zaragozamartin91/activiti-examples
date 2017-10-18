package mz.ex.activiti.check;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Check implements Serializable {
	// RUTA DE COBRO ------------------------------------------------------------
	private String bank;
	private String branchOffice;
	private String postalCode;
	private int routeDigit;

	// NUMERO DE CUENTA ------------------------------------------------------------
	private String account;
	private int accountDigit;

	// NUMERO DE CHEQUE ------------------------------------------------------------
	private String check;
	private int checkDigit;

	// IMPORTE ------------------------------------------------------------
	private BigDecimal amount;

	// FECHA ------------------------------------------------------------
	private Date date;

	// CUIT ------------------------------------------------------------
	private String cuit;

	public Check() {
	}

	public Check(String bank, String check) {
		this.bank = bank;
		this.check = check;
	}

	public Check(String bank, String branchOffice, String postalCode, int routeDigit, String account, int accountDigit, String check, int checkDigit, BigDecimal amount, Date date, String cuit) {
		this.bank = bank;
		this.branchOffice = branchOffice;
		this.postalCode = postalCode;
		this.routeDigit = routeDigit;
		this.account = account;
		this.accountDigit = accountDigit;
		this.check = check;
		this.checkDigit = checkDigit;
		this.amount = amount;
		this.date = date;
		this.cuit = cuit;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(String branchOffice) {
		this.branchOffice = branchOffice;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public int getRouteDigit() {
		return routeDigit;
	}

	public void setRouteDigit(int routeDigit) {
		this.routeDigit = routeDigit;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getAccountDigit() {
		return accountDigit;
	}

	public void setAccountDigit(int accountDigit) {
		this.accountDigit = accountDigit;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public int getCheckDigit() {
		return checkDigit;
	}

	public void setCheckDigit(int checkDigit) {
		this.checkDigit = checkDigit;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	@Override
	public String toString() {
		return "Check{" +
				"bank='" + bank + '\'' +
				", branchOffice='" + branchOffice + '\'' +
				", postalCode='" + postalCode + '\'' +
				", routeDigit=" + routeDigit +
				", account='" + account + '\'' +
				", accountDigit=" + accountDigit +
				", check='" + check + '\'' +
				", checkDigit=" + checkDigit +
				", amount=" + amount +
				", date=" + date +
				", cuit='" + cuit + '\'' +
				'}';
	}
}
