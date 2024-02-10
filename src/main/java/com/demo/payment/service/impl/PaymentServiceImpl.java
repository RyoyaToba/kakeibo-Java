package com.demo.payment.service.impl;

import com.demo.payment.entity.Account;
import com.demo.payment.repository.PaymentMapper;
import com.demo.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    /** 口座登録 */
    public void insert(Account account) {
        paymentMapper.insert(account);
    }

}
