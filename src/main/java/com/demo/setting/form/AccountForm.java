package com.demo.setting.form;

import javax.validation.constraints.NotBlank;

public class AccountForm {

    @NotBlank(message = "名称は必須です")
    private String name;

    @NotBlank(message = "残高は必須です")
    private String balance;

    @NotBlank(message = "タイプの選択は必須です")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
