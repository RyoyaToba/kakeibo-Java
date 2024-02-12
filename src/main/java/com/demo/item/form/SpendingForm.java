package com.demo.item.form;

public class SpendingForm {

    private String name;

    private Integer price;

    private Integer categoryId;

    private String targetDate;

    private Integer bankSelectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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
}
