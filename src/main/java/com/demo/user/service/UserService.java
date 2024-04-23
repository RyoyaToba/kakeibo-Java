package com.demo.user.service;

import com.demo.user.entity.User;

public interface UserService {

    User select(String userId);

    void insert(User user);




}
