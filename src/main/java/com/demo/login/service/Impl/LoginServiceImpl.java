package com.demo.login.service.Impl;

import com.demo.login.entity.LoginInformation;
import com.demo.login.repository.LoginMapper;
import com.demo.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    public LoginMapper loginMapper;

    @Override
    public LoginInformation loadByUserId(String userId) {
        return loginMapper.loadByUserId(userId);
    }

    @Override
    public void insert(LoginInformation loginInfo) {
        loginMapper.insert(loginInfo);
    }
}
