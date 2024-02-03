package com.demo.service;

import com.demo.Model.ItemUI;
import com.demo.entity.Category;
import com.demo.entity.Item;

import java.util.Date;
import java.util.List;

public interface ItemService {

    void insertItem(Item item);

    List<Item> retrieveItemInTargetMonth(Date startDate, Date endDate);

    List<ItemUI> convertItemToItemUI(List<Item> items, List<Category> categories);

}
