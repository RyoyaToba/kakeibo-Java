package com.demo.registration.controller;

import com.demo.category.entity.Category;
import com.demo.category.service.CategoryService;
import com.demo.common.service.CommonService;
import com.demo.common.utils.DateUtils;
import com.demo.common.utils.ItemUtils;
import com.demo.item.entity.Item;
import com.demo.registration.form.SpendingForm;
import com.demo.item.service.ItemService;
import com.demo.payment.entity.BankAccount;
import com.demo.payment.service.PaymentService;
import com.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/registration")
public class registrationController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private HttpSession session;

    @ModelAttribute
    private SpendingForm setSpendingForm() {
        return new SpendingForm();
    }

    /**
     * item画面表示
     * @param model モデル
     * @return item登録画面
     */
    @RequestMapping("")
    public String registrationPage(Model model) {

        User user = (User) session.getAttribute("user");

        // categoryを全件取得
        List<Category> categories = categoryService.selectAll();
        model.addAttribute("categories", categories);

        // 銀行口座の情報を全件取得
        List<BankAccount> accounts = paymentService.loadByUserId(user.getUserId());
        model.addAttribute("bankAccounts", accounts);

        // テーブルに登録されているユーザ全ItemのtargetDateを取得し、yyyy/mm形式で取得
        List<Item> items = itemService.retrieveItemAll(user.getUserId());

        List<String> targetDateListInDbs = items.stream()
                                                .map(Item::getTargetDate)
                                                .distinct()
                                                .map(DateUtils::dateToString)
                                                .toList();

        model.addAttribute("targetDateListInDbs", targetDateListInDbs);

        return "registration";
    }

    /**
     * Item入力結果を登録する
     * @param spendingForm item入力フォーム
     * @param result 入力バリデーション
     * @return home画面へ遷移
     */
    @RequestMapping("/spending")
    public String submitContents(
            @Validated SpendingForm spendingForm
            , BindingResult result
            , Model model
            ) {

        if (result.hasErrors()) {
            return registrationPage(model);
        }

        User user = (User)session.getAttribute("user");

        Item item = ItemUtils.formToItem(spendingForm);
        item.setUserId(user.getUserId());
        itemService.insertItem(item);

        return "redirect:/home";
    }

    @RequestMapping("/targetMonthData")
    public String targetMonthData(String targetMonth, Model model) {

        System.out.println(targetMonth);
        Date firstDate = DateUtils.convertLocalDateToDate(
                commonService.convertStringToFirstLocalDate(targetMonth)
        );
        Date endDate = DateUtils.convertLocalDateToDate(
                commonService.convertStringToEndLocalDate(targetMonth)
        );

        List<Item> items = itemService.retrieveItemInTargetMonth(firstDate, endDate);

        session.setAttribute("targetMonth", targetMonth);
        session.setAttribute("items", items);

        return "redirect:/registration";
    }




}
