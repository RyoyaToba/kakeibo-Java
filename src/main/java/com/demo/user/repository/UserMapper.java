package com.demo.user.repository;

import com.demo.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /** 登録 */
    void insert(User user);

    /** 取得 */
    User select(String userId);
}
