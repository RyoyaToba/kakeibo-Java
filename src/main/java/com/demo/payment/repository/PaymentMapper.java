package com.demo.payment.repository;

import com.demo.payment.entity.BankAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface PaymentMapper {

    /** 口座登録 */
    void insert(BankAccount account);

    /** 口座全件取得 */
    List<BankAccount> loadByUserId(String userId);

    /** 残高の更新 */
    void updateBankAccount(String userId, Integer accountId, Integer balance, Date updatedDate);

    /** 特定ユーザの特定アカウントを取得 */
    BankAccount loadByUserIdAndAccountId(String userId, Integer accountId);
}
