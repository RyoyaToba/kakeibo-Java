package com.demo.payment.service;

import com.demo.payment.entity.BankAccount;
import com.demo.payment.model.BankAccountUI;
import com.demo.setting.service.BankAccountMonthlyService;

import java.util.Date;
import java.util.List;

public interface PaymentService {

    /** 口座登録 */
    void insert(BankAccount account);

    /** 口座全件取得 */
    List<BankAccount> loadByUserId(String userId);

    /** 特定ユーザの特定アカウントを取得 */
    BankAccount loadByUserIdAndAccountId(String userId, Integer accountId);

    /** UIベースに変換 */
    List<BankAccountUI> convertAccountToAccountUI(List<BankAccount> accounts);

    /** 残高の更新 */
    void updateBankAccount(String userId, Integer accountId, Integer balance, Date updatedDate);

}
