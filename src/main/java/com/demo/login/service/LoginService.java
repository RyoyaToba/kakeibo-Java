package com.demo.login.service;

import com.demo.login.entity.LoginInformation;

public interface LoginService {

    LoginInformation loadByUserId(String userId);

    void insert(LoginInformation loginInfo);

}
