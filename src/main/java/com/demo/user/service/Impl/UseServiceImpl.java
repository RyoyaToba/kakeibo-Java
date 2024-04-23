package com.demo.user.service.Impl;

import com.demo.user.entity.User;
import com.demo.user.repository.UserMapper;
import com.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UseServiceImpl implements UserService {

    @Autowired
    public UserMapper userMapper;


    @Override
    public User select(String userId) {
        return userMapper.select(userId);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }
}
