package com.demo.category.model;

import lombok.Data;

@Data
public class CategoryUI {

    /** カテゴリId*/
    private Integer id;

    /** カテゴリー名 */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
