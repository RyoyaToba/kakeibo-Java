package com.demo.setting.service;

import com.demo.setting.model.BankAccountMonthlyModel;

public interface BankAccountMonthlyService {

    /** 登録 */
    int insert(BankAccountMonthlyModel model);

    /** 特定の基準年月のアカウント情報を取得 */
    BankAccountMonthlyModel selectByTargetYm(String targetYm, String userId, Integer accountId);

    /** 特定の基準年月の残高を更新 */
    int[] updateBankAccountMonthly(String targetYm, String userId, Integer accountId, Integer balance);
}
