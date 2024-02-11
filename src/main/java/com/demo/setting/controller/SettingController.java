package com.demo.setting.controller;

import com.demo.master.entity.AccountTypeMaster;
import com.demo.payment.entity.Account;
import com.demo.payment.model.AccountUI;
import com.demo.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("")
    public String settingPage(Model model) {

        // 登録済み口座を全件取得
        List<Account> accounts = paymentService.selectAll();
        // UIベースに変換
        List<AccountUI> accountUIs = paymentService.convertAccountToAccountUI(accounts);

        model.addAttribute("accounts", accountUIs);

        return "setting";
    }

    @RequestMapping("/account/registration")
    public String accountRegistration(String name, String type, String balance) {

        Integer typeInt = Integer.parseInt(type);
        Integer balanceInt = Integer.parseInt(balance);

        Account account = new Account();
        account.setName(name);
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
