package com.demo.service.Impl;

import com.demo.Model.ItemUI;
import com.demo.entity.Category;
import com.demo.entity.Item;
import com.demo.repository.ItemMapper;
import com.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
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

    /** 対象月内の入力済み情報を取得 */
    @Override
    public List<Item> retrieveItemInTargetMonth(Date startDate, Date endDate) {
        return itemMapper.retrieveItemInTargetMonth(startDate, endDate);
    }

    @Override
    public List<ItemUI> convertItemToItemUI(List<Item> items, List<Category> categories){
        return items.stream().map(item -> {
            ItemUI itemUI = new ItemUI();
            itemUI.setName(item.getName());
            itemUI.setPrice(item.getPrice());
            itemUI.setCategory(item.convertCategoryIdToCategory(categories));
            return itemUI;
        }).collect(Collectors.toList());
    }


}
