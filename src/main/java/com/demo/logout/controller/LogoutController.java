package com.demo.logout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    /**
     * ログアウトし、ログイン画面へ遷移
     * @return
     */
    @RequestMapping("")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate(); // セッションを無効化して属性を削除
        }
        return "login";
    }

}
