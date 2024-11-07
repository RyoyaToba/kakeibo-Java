package com.demo.login.controller;

import com.demo.category.entity.Category;
import com.demo.category.service.CategoryService;
import com.demo.common.utils.CommonUtils;
import com.demo.common.utils.DateUtils;
import com.demo.item.entity.Item;
import com.demo.item.model.ItemUI;
import com.demo.item.service.ItemService;
import com.demo.login.entity.LoginInformation;
import com.demo.login.service.LoginService;
import com.demo.user.entity.User;
import com.demo.user.service.UserService;
import com.demo.setting.model.Withdrawal;
import com.demo.setting.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public LoginService loginService;

    @Autowired
    public UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private WithdrawalService withdrawalService;

    /**
     * ログインページの表示
     * @return
     */
    @RequestMapping("")
    public String loginPage() {
        return "login";
    }

    /**
     * ログイン処理
     * @param userId
     * @param password
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/check")
    public String loginCheck(String userId, String password, HttpSession session, Model model) {

        LoginInformation loginInfo = loginService.loadByUserId(userId);

        if (userId.isEmpty() || password.isEmpty()){
            String notUserMessage = "ユーザID、パスワードを入力してください";
            model.addAttribute("notUserMessage", notUserMessage);
            return "login";
        }

        if (!password.equals(loginInfo.getPassword())) {
            String notUserMessage = "入力されたユーザID、パスワードに誤りがあります";
            model.addAttribute("notUserMessage", notUserMessage);
            return "login";
        }

        User user = userService.select(userId);

        session.setAttribute("user", user);

        //--------------------------------------------------
        // 以下ログイン時にhome画面でデータを表示させるために取得する
        //--------------------------------------------------

        LocalDate targetDate = LocalDate.now();
        String targetMonth = DateUtils.localDateToStringTitleMonth(targetDate);

        // categoryを全件取得
        List<Category> categories = categoryService.selectAll();
        model.addAttribute("categories", categories);
        // 対象月の月初
        LocalDate startDate = DateUtils.getStartOfMonth(targetDate);
        // 対象月の月末
        LocalDate endDate = DateUtils.getEndOfMonth(targetDate);
        // 対象月内の登録データ取得
        List<Item> itemsInTargetMonth = itemService.retrieveItemInTargetMonth(
                userId,
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
}
