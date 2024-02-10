package com.demo.payment.service;

import com.demo.payment.entity.Account;

public interface PaymentService {

    /** 口座登録 */
    void insert(Account account);

}
