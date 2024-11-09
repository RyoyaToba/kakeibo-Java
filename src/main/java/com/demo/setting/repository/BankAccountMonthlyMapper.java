package com.demo.setting.repository;

import com.demo.setting.model.BankAccountMonthlyModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BankAccountMonthlyMapper {

    /** 登録 */
    int insert(BankAccountMonthlyModel model);

    /** 特定の基準年月のアカウント情報を取得 */
    BankAccountMonthlyModel selectByTargetYm(String targetYm, String userId, Integer accountId);

    /** 特定の月別口座情報を取得 */
    List<BankAccountMonthlyModel> selectByTargetBanks(String userId, Integer accountId);

    /** 月次残高が入力されている口座情報を重複なく取得する **/
    List<BankAccountMonthlyModel> retrieveBankName(String userId);

    /** 特定の基準年月の残高を更新 */
    int[] updateBankAccountMonthly(String targetYm, String userId, Integer accountId, Integer balance);

}
