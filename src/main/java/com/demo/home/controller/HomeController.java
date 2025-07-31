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

        LocalDate targetDate = DateUtils.resolveTargetDate(targetMonth);
        String titleMonth = DateUtils.localDateToStringTitleMonth(targetDate);
        LocalDate start = DateUtils.getStartOfMonth(targetDate);
        LocalDate end = DateUtils.getEndOfMonth(targetDate);

        List<Category> categories = categoryService.selectAll();
        List<Item> items = itemService.retrieveItemInTargetMonth(
            user.getUserId(), 
            DateUtils.convertLocalDateToDate(start), 
            DateUtils.convertLocalDateToDate(end)
        );

        List<ItemUI> displayItems = itemService.convertItemToItemUI(items, categories);
        List<Withdrawal> withdrawals = withdrawalService.createWithdrawal(
            withdrawalService.calcSumprice(items), 
            user.getUserId()
        );

        model.addAttribute("categories", categories);
        model.addAttribute("items", displayItems);
        model.addAttribute("existsItems", !items.isEmpty());
        model.addAttribute("months", CommonUtils.retrieveMonths());
        model.addAttribute("titleMonth", titleMonth);
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
