package com.demo.item.service.impl;

import com.demo.item.entity.ItemSummarize;
import com.demo.item.model.ItemUI;
import com.demo.category.entity.Category;
import com.demo.item.entity.Item;
import com.demo.item.repository.ItemMapper;
import com.demo.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Transactional()
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    public ItemMapper itemMapper;

    /** 新規レコードの追加 */
    @Override
    public void insertItem(Item item) {
        itemMapper.insertItem(item);
    }

    /** 新規レコードをまとめて追加 */
    @Override
    public void multiInsertItems(List<Item> items) {
        itemMapper.multiInsertItems(items);
    }

    /** 対象月内の入力済み情報を取得 */
    @Override
    public List<Item> retrieveItemInTargetMonth(Date startDate, Date endDate) {
        return itemMapper.retrieveItemInTargetMonth(startDate, endDate);
    }

    /** ItemのテーブルデータをUI状態に置き換える */
    @Override
    public List<ItemUI> convertItemToItemUI(List<Item> items, List<Category> categories){
        return items.stream().map(item -> {
            ItemUI itemUI = new ItemUI();
            itemUI.setItemId(item.getItemId());
            itemUI.setName(item.getName());
            itemUI.setPrice(item.getPrice());
            itemUI.setCategory(item.convertCategoryIdToCategory(categories));
            return itemUI;
        }).collect(Collectors.toList());
    }

    /** カテゴリー毎に金額を集計する */
    @Override
    public Map<Integer, Integer> totalAmountPerCategory(List<Item> items) {
        // itemsをカテゴリーIDごとに集計する。(key:categoryId | value:totalPrice)
        return items.stream().collect(Collectors.groupingBy(
                        Item::getCategoryId,
                Collectors.summingInt(Item::getPrice)
        ));
    }

    /** itemを削除する */
    @Override
    public void deleteItem(Integer id, String userId) {
        itemMapper.deleteItem(id, userId);
    }

    /** itemの金額を編集する */
    @Override
    public void updateItem(String userId, Integer itemId, Integer price, Date now) {
        itemMapper.updateItem(userId, itemId, price, now);
    }

    @Override
    public List<Item> retrieveItemAll(String userId) {
        return itemMapper.retrieveItemAll(userId);
    }

    /** まとめて作成したItemModelからItemModelへ詰め替える */
    @Override
    public List<Item> convertItemSummarizeToItem(List<ItemSummarize> itemSummarizes) {
        return itemSummarizes.stream().map(itemSummarize -> {
            Item item = new Item();
            item.setUserId(itemSummarize.getUserId());
            item.setName(itemSummarize.getName());
            item.setPrice(itemSummarize.getPrice());
            item.setCategoryId(itemSummarize.getCategoryId());
            item.setBankSelectId(itemSummarize.getBankSelectId());
            item.setCreatedBy(itemSummarize.getUserId());
            item.setCreatedDate(new Date());
            item.setUpdatedBy(itemSummarize.getUserId());
            item.setUpdatedDate(new Date());
            return item;
        }).collect(Collectors.toList());
    }

    @Override
    /** 合計金額を月毎でまとめる */
    public Map<String, Integer> calculateMonthlyTotal(List<Item> items) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        return items.stream()
                .collect(Collectors.groupingBy(
                        item -> dateFormat.format(item.getTargetDate()),
                        TreeMap::new,
                        Collectors.summingInt(Item::getPrice)
                ));
    }
}
