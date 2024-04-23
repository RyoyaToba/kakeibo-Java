package com.demo.home.controller;

import com.demo.item.model.ItemUI;
import com.demo.common.service.CommonService;
import com.demo.common.utils.CommonUtils;
import com.demo.common.utils.DateUtils;
import com.demo.category.entity.Category;
import com.demo.item.entity.Item;
import com.demo.registration.form.SpendingForm;
import com.demo.category.service.CategoryService;
import com.demo.item.service.ItemService;
import com.demo.user.entity.User;
import com.demo.withdrawal.Model.Withdrawal;
import com.demo.withdrawal.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
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
    private WithdrawalService  withdrawalService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private HttpSession session;

    @ModelAttribute
    private SpendingForm setSpendingForm() {
        return new SpendingForm();
    };

    /**
     * indexPageを表示
     * @return index.html
     */
    @RequestMapping("")
    public String index(String targetMonth, Model model) {

        User user = (User) session.getAttribute("user");

        LocalDate targetDate = null;

        // 初期表示の場合は当月データを取得する
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

        // 引き落とし口座ごとに金額をまとめ、引き落としオブジェクトのリストを返す
        List<Withdrawal> withdrawals = withdrawalService.createWithdrawal(
                withdrawalService.calcSumprice(itemsInTargetMonth)
                , user.getUserId()
        );

        // 各口座からの引き落とし金額
        model.addAttribute("withdrawals", withdrawals);

        return "index";
    }


    /**
     * itemの削除
     * @return
     */
    @RequestMapping("/delete")
    public String deleteItem(Integer itemId) {
        User user = (User) session.getAttribute("user");
        itemService.deleteItem(itemId, user.getUserId());
        return "redirect:/home";
    }

}
