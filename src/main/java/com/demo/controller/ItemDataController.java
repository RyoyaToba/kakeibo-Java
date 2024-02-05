package com.demo.controller;

import com.demo.Model.ItemUI;
import com.demo.entity.Category;
import com.demo.entity.Item;
import com.demo.service.CategoryService;
import com.demo.service.CommonService;
import com.demo.service.ItemService;
import com.demo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ItemDataController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommonService commonService;

    @RequestMapping("/retrieve")
    public List<ItemUI> retrieveItem(@RequestBody Map<String, String> requestBody) {

        String selectedMonth = requestBody.get("selectedMonth");
        LocalDate targetDate = commonService.convertStringToFirstLocalDate(selectedMonth);

        // categoryを全件取得
        List<Category> categories = categoryService.selectAll();

        // 対象月の月初
        LocalDate startDate = DateUtils.getStartOfMonth(targetDate);
        // 対象月の月末
        LocalDate endDate = DateUtils.getEndOfMonth(targetDate);
        // 対象月内の登録データ取得
        List<Item> itemsInTargetMonth = itemService.retrieveItemInTargetMonth(
                DateUtils.convertLocalDateToDate(startDate),
                DateUtils.convertLocalDateToDate(endDate));
        // UI表示形式に変換する
        return itemService.convertItemToItemUI(itemsInTargetMonth, categories);
    }

}
