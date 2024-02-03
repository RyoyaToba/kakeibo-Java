package com.demo.controller;

import com.demo.Model.ItemUI;
import com.demo.utils.DateUtils;
import com.demo.utils.ItemUtils;
import com.demo.entity.Category;
import com.demo.entity.Item;
import com.demo.form.SpendingForm;
import com.demo.service.CategoryService;
import com.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    private SpendingForm setSpendingForm() {
        return new SpendingForm();
    };

    /**
     * index Page
     * @return index.html
     */
    @RequestMapping("")
    public String index(Model model) {

        // categoryを全件取得
        List<Category> categories = categoryService.selectAll();
        model.addAttribute("categories", categories);

        // 対象月の月初
        LocalDate startDate = DateUtils.getStartOfMonth(LocalDate.now());
        // 対象月の月末
        LocalDate endDate = DateUtils.getEndOfMonth(LocalDate.now());
        // 対象月内の登録データ取得
        List<Item> itemsInTargetMonth = itemService.retrieveItemInTargetMonth(
                                            DateUtils.convertLocalDateToDate(startDate),
                                            DateUtils.convertLocalDateToDate(endDate));

        // UI表示形式に変換する
        List<ItemUI> itemUIs = itemService.convertItemToItemUI(itemsInTargetMonth, categories);

        model.addAttribute("items", itemUIs);

        return "index";
    }

    @RequestMapping("/spending")
    public String submitContents(SpendingForm spendingForm) {

        Item item = ItemUtils.formToItem(spendingForm);
        itemService.insertItem(item);

        return "redirect:/home";
    }

}
