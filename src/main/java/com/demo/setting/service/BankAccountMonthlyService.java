package com.demo.setting.service;

import com.demo.setting.model.BankAccountMonthlyModel;

import java.util.List;
import java.util.Map;

public interface BankAccountMonthlyService {

    /** 登録 */
    int insert(BankAccountMonthlyModel model);

    /** 特定の基準年月のアカウント情報を取得 */
    BankAccountMonthlyModel selectByTargetYm(String targetYm, String userId, Integer accountId);

    /** 特定の月別口座情報を取得 */
    Map<String, Integer> selectByTargetBanks(String userId, Integer accountId);

    /** 月次残高が登録されている口座情報を重複なく取得 **/
    List<BankAccountMonthlyModel> retrieveBankName(String userId);

    /** 特定の基準年月の残高を更新 */
    int[] updateBankAccountMonthly(String targetYm, String userId, Integer accountId, Integer balance);
}
