package com.demo.login.repository;

import com.demo.login.entity.LoginInformation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    LoginInformation loadByUserId(String userId);

    void insert(LoginInformation loginInformation);



}
