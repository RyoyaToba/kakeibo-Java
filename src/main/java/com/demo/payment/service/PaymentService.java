package com.demo.payment.service;

import com.demo.payment.entity.Account;
import com.demo.payment.model.AccountUI;

import java.util.List;

public interface PaymentService {
    /** 口座登録 */
    void insert(Account account);
    /** 口座全件取得 */
    List<Account> selectAll();
    /** UIベースに変換 */
    List<AccountUI> convertAccountToAccountUI(List<Account> accounts);

}
