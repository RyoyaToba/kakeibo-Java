package com.demo.payment.service;

import com.demo.payment.entity.BankAccount;
import com.demo.payment.model.BankAccountUI;

import java.util.List;

public interface PaymentService {
    /** 口座登録 */
    void insert(BankAccount account);
    /** 口座全件取得 */
    List<BankAccount> loadByUserId(String userId);
    /** UIベースに変換 */
    List<BankAccountUI> convertAccountToAccountUI(List<BankAccount> accounts);

}
