package com.demo.setting.controller;

import com.demo.payment.entity.Account;
import com.demo.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("")
    public String settingPage() {
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
