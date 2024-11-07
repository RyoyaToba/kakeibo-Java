package com.demo.payment.controller;

import com.demo.payment.service.PaymentService;
import com.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PaymentService paymentService;

    /**
     * 残高の更新
     * @param accountId
     * @param newBalance
     * @return
     */
    @RequestMapping("/updateBankAccount")
    public String updateBankAccount(Integer accountId, Integer newBalance) {

        User user = (User) session.getAttribute("user");

        // 残高更新
        paymentService.updateBankAccount(
                user.getUserId()
                , accountId
                , newBalance
                , new Date()
        );

        return "redirect:/setting";
    }

}
