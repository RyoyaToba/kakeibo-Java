package com.demo.dashboard.controller;

import com.demo.setting.model.BankAccountMonthlyModel;
import com.demo.setting.service.BankAccountMonthlyService;
import com.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BankAccountMonthlyService bankAccountMonthlyService;


    @RequestMapping("")
    public String dashboardPage(Model model) {

        User user = (User) session.getAttribute("user");

        List<BankAccountMonthlyModel> banks = bankAccountMonthlyService.retrieveBankName(user.getUserId());

        model.addAttribute("banks", banks);

        return "dashboard";
    }
    // コメント２
}
