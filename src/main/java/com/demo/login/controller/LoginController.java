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

        session.setAttribute("user", userService.select(userId));

        return "redirect:/home";
    }
}
