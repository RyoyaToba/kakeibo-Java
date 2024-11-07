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
import com.demo.setting.model.Withdrawal;
import com.demo.setting.service.WithdrawalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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
                user.getUserId(),
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


    /***
     * 表内の金額を編集する
     * @param request
     * @return
     */
    @RequestMapping("/updateCellValue")
    public String updateCellValue(@RequestBody Map<String, Object> request) {

        User user = (User) session.getAttribute("user");

        String userId = user.getUserId();
        Integer itemId = Integer.parseInt((String) request.get("itemId"));
        Integer newValue = Integer.parseInt((String) request.get("newValue"));

        itemService.updateItem(userId, itemId, newValue, new Date());

        return "index";
    }

    /**
     * 前月分の合計金額を計算し、JS側に渡す。
     * @param previousMonth
     */
    @ResponseBody
    @PostMapping("/calcTotalPricePrevMonth")
    public Map<String, Integer> receivePreviousMonth(@RequestBody Map<String, String> previousMonth) {

        String previousMonthString = previousMonth.get("previousMonth");

        User user = (User) session.getAttribute("user");

        // 受け取った前月の文字列を処理する
        LocalDate previousMonthLD = commonService.convertStringToFirstLocalDate(previousMonthString);

        // 対象月の月初
        LocalDate startDate = DateUtils.getStartOfMonth(previousMonthLD);
        // 対象月の月末
        LocalDate endDate = DateUtils.getEndOfMonth(previousMonthLD);

        List<Item> items =
                itemService.retrieveItemInTargetMonth(
                        user.getUserId(),
                        DateUtils.convertLocalDateToDate(startDate)
                        , DateUtils.convertLocalDateToDate(endDate)
                );

        Integer previousMonthSum = items.stream().mapToInt(Item::getPrice).sum();

        Map<String, Integer> map = new HashMap<>();
        map.put("previousMonthSum", previousMonthSum);

        return map;

    }
}
