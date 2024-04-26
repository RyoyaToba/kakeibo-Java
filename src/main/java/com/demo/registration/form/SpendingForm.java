package com.demo.registration.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SpendingForm {

    @NotBlank(message = "名称は必須です")
    private String name;

    @NotBlank(message = "金額は必須です")
    private String price;

    private Integer categoryId;

    @NotNull(message = "日付入力は必須です")
    private String targetDate;

    @NotNull(message = "引き落としの口座選択は必須です")
    private Integer bankSelectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public Integer getBankSelectId() {
        return bankSelectId;
    }

    public void setBankSelectId(Integer bankSelectId) {
        this.bankSelectId = bankSelectId;
    }

    @Override
    public String toString() {
        return "SpendingForm{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", categoryId=" + categoryId +
                ", targetDate='" + targetDate + '\'' +
                ", bankSelectId=" + bankSelectId +
                '}';
    }
}
