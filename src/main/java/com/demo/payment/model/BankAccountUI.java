package com.demo.payment.model;

public class BankAccountUI {
    /** 口座ID */
    private Integer accountId;
    /** 口座名称 */
    private String accountName;
    /** 口座タイプ名称*/
    private String accountTypeName;
    /** 残高 */
    private Integer balance;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
