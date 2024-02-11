package com.demo.payment.model;

import java.util.Date;

public class AccountUI {
    /** 口座ID */
    private Integer id;
    /** 口座名称 */
    private String accountName;
    /** 口座タイプ名称*/
    private String accountTypeName;
    /** 残高 */
    private Integer balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
