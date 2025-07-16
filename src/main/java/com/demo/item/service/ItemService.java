package com.demo.item.service;

import com.demo.item.entity.ItemSummarize;
import com.demo.item.model.ItemUI;
import com.demo.category.entity.Category;
import com.demo.item.entity.Item;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ItemService {

    /** ItemのINSERT */
    void insertItem(Item item);

    void multiInsertItems(List<Item> items);

    /** 月内のItem情報を取得 */
    List<Item> retrieveItemInTargetMonth(String userId, Date startDate, Date endDate);

    /** テーブル格納情報をUI状態に変更 */
    List<ItemUI> convertItemToItemUI(List<Item> items, List<Category> categories);

    /** カテゴリー毎に金額を集計する */
    Map<Integer, Integer> totalAmountPerCategory(List<Item> items);

    void deleteItem(Integer itemId, String userId);

    void updateItem(String userId, Integer itemId, Integer price, Date now);

    /** 登録済みのItemの日付を全件取得する */
    List<Item> retrieveItemAll(String userId);

    /** まとめて入力されたItemSummarizeをItemに詰め替える */
    List<Item> convertItemSummarizeToItem(List<ItemSummarize> itemSummarizes);

    /** 合計金額を月毎で集計する */
    Map<String, Integer> calculateMonthlyTotal(List<Item> items);

    /** Itemの金額を返す **/
    Map<String, Integer> retrieveItemsByName(String userId, String itemName);

}
