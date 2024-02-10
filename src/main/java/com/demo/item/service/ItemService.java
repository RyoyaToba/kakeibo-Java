package com.demo.item.service;

import com.demo.item.model.ItemUI;
import com.demo.category.entity.Category;
import com.demo.item.entity.Item;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ItemService {

    /** ItemのINSERT */
    void insertItem(Item item);

    /** 月内のItem情報を取得 */
    List<Item> retrieveItemInTargetMonth(Date startDate, Date endDate);

    /** テーブル格納情報をUI状態に変更 */
    List<ItemUI> convertItemToItemUI(List<Item> items, List<Category> categories);

    /** カテゴリー毎に金額を集計する */
    Map<Integer, Integer> totalAmountPerCategory(List<Item> items);

    void deleteItem(Integer id);

}
