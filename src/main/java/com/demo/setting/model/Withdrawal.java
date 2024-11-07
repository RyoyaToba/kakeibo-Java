package com.demo.setting.model;

public class Withdrawal {
    /** 口座名称 */
    private String bankName;
    /** 金額 */
    private Integer sumPrice;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
    }
}
