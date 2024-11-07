package com.demo.item.repository;

import com.demo.item.entity.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ItemMapper {

    /** 登録 */
    void insertItem(Item item);

    /** 複数をまとめて登録 */
    void multiInsertItems(List<Item> items);

    /** 対象月内の入力情報を取得 */
    List<Item> retrieveItemInTargetMonth(String userId, Date startDate, Date endDate);

    /** 削除 */
    void deleteItem(Integer itemId, String userId);

    /** Itemの金額を更新する */
    void updateItem(String userId, Integer itemId, Integer price, Date now);

    /** 登録済みItemの日付を全件取得する */
    List<Item> retrieveItemAll(String userId);

}
