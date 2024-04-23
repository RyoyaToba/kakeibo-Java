package com.demo.item.model;

public class ItemUI {

    /** id */
    private Integer itemId;
    /** 名称 */
    private String name;
    /** 価格 */
    private Integer price;
    /** カテゴリ */
    private String category;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
