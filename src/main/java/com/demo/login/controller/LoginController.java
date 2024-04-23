package com.demo.login.controller;

import com.demo.login.entity.LoginInformation;
import com.demo.login.service.LoginService;
import com.demo.user.entity.User;
import com.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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

        System.out.println(userId + " " + password);

        LoginInformation loginInfo = loginService.loadByUserId(userId);

        if (loginInfo == null) {
            String notUserMessage = "入力されたユーザID、パスワードに誤りがあります";
            model.addAttribute("notUserMessage", notUserMessage);
            return "login";
        }

        User user = userService.select(userId);

        session.setAttribute("user", user);

        return "index";
    }
}
