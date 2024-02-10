package com.demo.payment.repository;

import com.demo.payment.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    /** 口座登録 */
    void insert(Account account);

}
