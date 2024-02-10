package com.demo.item.repository;

import com.demo.item.entity.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ItemMapper {

    /** 登録 */
    void insertItem(Item item);

    /** 対象月内の入力情報を取得 */
    List<Item> retrieveItemInTargetMonth(Date startDate, Date endDate);

    /** 削除 */
    void deleteItem(Integer id);

}
