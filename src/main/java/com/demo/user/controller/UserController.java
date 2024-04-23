package com.demo.user.controller;

import com.demo.login.entity.LoginInformation;
import com.demo.login.service.LoginService;
import com.demo.user.entity.User;
import com.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public LoginService loginService;

    /**
     * ユーザ新規作成画面への遷移
     */
    @RequestMapping("")
    public String createNewUserPage() {
        return "createNewUser";
    }

    @RequestMapping("/createNew")
    public String createNew(String userId, String password) {

        // 入力バリデーションチェック

        // パスワードエンコード

        // userId重複チェック

        // パスワード制限チェック

        // ユーザ登録
        User user = new User();
        user.setUserId(userId);
        user.setMailAddress(null);
        user.setCreatedBy("T00001");
        user.setCreatedDate(new Date());
        user.setUpdatedBy("T00001");
        user.setUpdatedDate(new Date());

        userService.insert(user);

        LoginInformation loginInfo = new LoginInformation();
        loginInfo.setUserId(userId);
        loginInfo.setPassword(password);
        loginInfo.setCreatedBy("T00001");
        loginInfo.setCreatedDate(new Date());
        loginInfo.setUpdatedBy("T00001");
        loginInfo.setUpdatedDate(new Date());
        loginService.insert(loginInfo);

        return "login";

    }


}
