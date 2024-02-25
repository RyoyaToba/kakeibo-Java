package com.demo.registration.controller;

import com.demo.category.entity.Category;
import com.demo.category.service.CategoryService;
import com.demo.common.service.CommonService;
import com.demo.common.utils.ItemUtils;
import com.demo.item.entity.Item;
import com.demo.registration.form.SpendingForm;
import com.demo.item.service.ItemService;
import com.demo.payment.entity.Account;
import com.demo.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

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

        // categoryを全件取得
        List<Category> categories = categoryService.selectAll();
        model.addAttribute("categories", categories);

        // 銀行口座の情報を全件取得
        List<Account> accounts = paymentService.selectAll();
        model.addAttribute("bankAccounts", accounts);

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
            ,BindingResult result
            ,Model model
    ) {

        if (result.hasErrors()) {
            return registrationPage(model);
        }

        Item item = ItemUtils.formToItem(spendingForm);
        itemService.insertItem(item);

        return "redirect:/home";
    }

}
