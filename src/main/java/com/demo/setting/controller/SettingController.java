package com.demo.setting.controller;

import com.demo.payment.entity.BankAccount;
import com.demo.payment.model.BankAccountUI;
import com.demo.payment.service.PaymentService;
import com.demo.setting.form.AccountForm;
import com.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private PaymentService paymentService;

    @ModelAttribute
    private AccountForm setAccountForm() {
        return new AccountForm();
    }

    @RequestMapping("")
    public String settingPage(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        // 登録済み口座を全件取得
        List<BankAccount> accounts = paymentService.loadByUserId(user.getUserId());
        // UIベースに変換
        List<BankAccountUI> accountUIs = paymentService.convertAccountToAccountUI(accounts);

        model.addAttribute("accounts", accountUIs);

        return "setting";
    }

    @RequestMapping("/account/registration")
    public String accountRegistration(
            @Validated AccountForm accountForm
            , BindingResult result
            , Model model
            , HttpSession session

    ) {

        if (result.hasErrors()) {
            return settingPage(model, session);
        }

        User user = (User) session.getAttribute("user");

        Integer typeInt = Integer.parseInt(accountForm.getType());
        Integer balanceInt = Integer.parseInt(accountForm.getBalance());

        BankAccount account = new BankAccount();
        account.setUserId(user.getUserId());
        account.setName(accountForm.getName());
        account.setType(typeInt);
        account.setBalance(balanceInt);
        account.setCreatedBy("T00001");
        account.setCreatedDate(new Date());
        account.setUpdatedBy("T00001");
        account.setUpdatedDate(new Date());

        // 口座登録
        paymentService.insert(account);

        return "setting";
    }

}
