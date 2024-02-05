package com.demo.controller;

import com.demo.Model.ItemUI;
import com.demo.service.CommonService;
import com.demo.utils.CommonUtils;
import com.demo.utils.DateUtils;
import com.demo.utils.ItemUtils;
import com.demo.entity.Category;
import com.demo.entity.Item;
import com.demo.form.SpendingForm;
import com.demo.service.CategoryService;
import com.demo.service.ItemService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommonService commonService;

    @ModelAttribute
    private SpendingForm setSpendingForm() {
        return new SpendingForm();
    };

    /**
     * index Page
     * @return index.html
     */
    @RequestMapping("")
    public String index(String targetMonth, Model model) {

        LocalDate targetDate = null;

        if (targetMonth == null) {
            targetDate = LocalDate.now();
            targetMonth = DateUtils.localDateToStringTitleMonth(targetDate);
        } else {
            targetDate = commonService.convertStringToFirstLocalDate(targetMonth);
        }

        // categoryを全件取得
        List<Category> categories = categoryService.selectAll();
        model.addAttribute("categories", categories);
        // 対象月の月初
        LocalDate startDate = DateUtils.getStartOfMonth(targetDate);
        // 対象月の月末
        LocalDate endDate = DateUtils.getEndOfMonth(targetDate);
        // 対象月内の登録データ取得
        List<Item> itemsInTargetMonth = itemService.retrieveItemInTargetMonth(
                                            DateUtils.convertLocalDateToDate(startDate),
                                            DateUtils.convertLocalDateToDate(endDate));
        // UI表示形式に変換する
        List<ItemUI> itemUIs = itemService.convertItemToItemUI(itemsInTargetMonth, categories);
        // 年月選択用プルダウン
        model.addAttribute("months", CommonUtils.retrieveMonths());
        // 登録済み情報
        model.addAttribute("items", itemUIs);
        // title部分の日付 YYYY/MM形式
        model.addAttribute("titleMonth", targetMonth);

        return "index";
    }

    /**
     * Item入力結果を登録する
     * @param spendingForm
     * @return
     */
    @RequestMapping("/spending")
    public String submitContents(SpendingForm spendingForm) {

        Item item = ItemUtils.formToItem(spendingForm);
        itemService.insertItem(item);

        return "redirect:/home";
    }

}
