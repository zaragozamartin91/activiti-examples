package mz.ex.activiti.check;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Check implements Serializable {
    // RUTA DE COBRO ------------------------------------------------------------
    private String bank;
    private String branchOffice;
    private String postalCode;
    private Integer routeDigit;

    // NUMERO DE CUENTA ------------------------------------------------------------
    private String account;
    private Integer accountDigit;

    // NUMERO DE CHEQUE ------------------------------------------------------------
    private String number;
    private Integer checkDigit;

    // IMPORTE ------------------------------------------------------------
    private BigDecimal amount;

    // FECHA ------------------------------------------------------------
    private Date date;

    // CUIT ------------------------------------------------------------
    private String cuit;

    public Check() {
    }

    public Check(String bank, String number) {
        this.bank = bank;
        this.number = number;
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

    public Integer getRouteDigit() {
        return routeDigit;
    }

    public void setRouteDigit(Integer routeDigit) {
        this.routeDigit = routeDigit;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getAccountDigit() {
        return accountDigit;
    }

    public void setAccountDigit(Integer accountDigit) {
        this.accountDigit = accountDigit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(Integer checkDigit) {
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
                ", number='" + number + '\'' +
                ", checkDigit=" + checkDigit +
                ", amount=" + amount +
                ", date=" + date +
                ", cuit='" + cuit + '\'' +
                '}';
    }
}
