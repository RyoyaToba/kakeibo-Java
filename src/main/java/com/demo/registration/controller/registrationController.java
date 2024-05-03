package com.demo.registration.controller;

import com.demo.category.entity.Category;
import com.demo.category.service.CategoryService;
import com.demo.common.service.CommonService;
import com.demo.common.utils.CommonUtils;
import com.demo.common.utils.DateUtils;
import com.demo.common.utils.ItemUtils;
import com.demo.item.entity.Item;
import com.demo.item.entity.ItemSummarize;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        // 年月選択用プルダウン
        model.addAttribute("months", CommonUtils.retrieveMonths());

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


    @RequestMapping("/spending-summarize")
    public String spendingSummarize(String month, HttpServletRequest request) {

        User user = (User) session.getAttribute("user");

        String[] itemNames = request.getParameterValues("name");
        String[] itemPrices = request.getParameterValues("price");
        String[] categoryIds = request.getParameterValues("categoryId");
        String[] bankSelectIds = request.getParameterValues("bankSelectId");

        // まとめて入力されてきた要素をオブジェクトにまとめる。金額が入力されなかった要素は除く。
        List<ItemSummarize> itemSummarizes = IntStream.range(0, itemNames.length)
                .mapToObj(i -> {
                    // 空欄の場合はダミーの-1に置き換える
                    String priceStr = itemPrices[i].isEmpty() ? "-1" : itemPrices[i];
                    return new ItemSummarize(
                            user.getUserId()
                            ,itemNames[i]
                            ,Integer.parseInt(priceStr)
                            ,Integer.parseInt(categoryIds[i])
                            ,Integer.parseInt(bankSelectIds[i]));
                })
                .filter(item -> item.getPrice() != -1) // priceが-1でないものをフィルタリング
                .toList();

        // 対象月の月初
        Date targetDate = DateUtils.convertLocalDateToDate(
                commonService.convertStringToFirstLocalDate(month)
        );

        List<Item> items = itemService.convertItemSummarizeToItem(itemSummarizes).stream()
                .peek(e -> e.setTargetDate(targetDate)).toList();

        System.out.println("month " + month);

        itemSummarizes.stream().forEach(System.out::println);

        itemService.multiInsertItems(items);

        return "redirect:/registration";
    }

}
