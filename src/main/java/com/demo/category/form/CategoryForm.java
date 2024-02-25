package com.demo.category.form;

import javax.validation.constraints.NotBlank;

public class CategoryForm {

    @NotBlank(message = "カテゴリの名称は必須です")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
