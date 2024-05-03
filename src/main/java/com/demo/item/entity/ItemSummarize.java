package com.demo.item.entity;

import lombok.Getter;
import lombok.Setter;

public class ItemSummarize {

    @Getter
    @Setter
    /** ユーザID */
    private String userId;
    /** 名前 */
    @Getter
    @Setter
    private String name;
    /** 価格 */
    @Getter
    @Setter
    private Integer price;
    /** カテゴリーID */
    @Getter
    @Setter
    private Integer categoryId;
    /** 引き落とし銀行ID */
    @Getter
    @Setter
    private Integer bankSelectId;

    @Override
    public String toString() {
        return "ItemSummarize{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", bankSelectId=" + bankSelectId +
                '}';
    }

    public ItemSummarize(String userId, String name, Integer price, Integer categoryId, Integer bankSelectId) {
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.bankSelectId = bankSelectId;
    }

}
