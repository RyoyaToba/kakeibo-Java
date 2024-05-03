package com.demo.item.controller;

import com.demo.item.model.ItemUI;
import com.demo.category.entity.Category;
import com.demo.item.entity.Item;
import com.demo.category.service.CategoryService;
import com.demo.common.service.CommonService;
import com.demo.item.service.ItemService;
import com.demo.common.utils.DateUtils;
import com.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    private HttpSession session;

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


    /**
     * 月別合計金額を返す
     * @return 月別合計金額
     */
    @ResponseBody
    @RequestMapping("/sumPrice")
    public Map<String, Integer> retrieveSumPrice() {
        User user = (User) session.getAttribute("user");

        // 対象ユーザのItemを全件取得
        List<Item> items = itemService.retrieveItemAll(user.getUserId());

        // 合計金額を月毎で集計する
        Map<String, Integer> map = itemService.calculateMonthlyTotal(items);

        return map;
    }

}
