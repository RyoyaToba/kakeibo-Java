package com.demo.setting.service.impl;

import com.demo.setting.model.BankAccountMonthlyModel;
import com.demo.setting.repository.BankAccountMonthlyMapper;
import com.demo.setting.service.BankAccountMonthlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountMonthlyServiceImpl implements BankAccountMonthlyService {

    @Autowired
    private BankAccountMonthlyMapper bankAccountMonthlyMapper;

    /** 登録 */
    @Override
    public int insert(BankAccountMonthlyModel model) {
        return bankAccountMonthlyMapper.insert(model);
    }

    /** 取得 */
    @Override
    public BankAccountMonthlyModel selectByTargetYm(String targetYm, String userId, Integer accountId) {
        return bankAccountMonthlyMapper.selectByTargetYm(targetYm, userId, accountId);
    }

    /** 特定口座情報を全件取得 **/
    @Override
    public Map<String, Integer> selectByTargetBanks(String userId, Integer accountId) {
        List<BankAccountMonthlyModel> banks = bankAccountMonthlyMapper.selectByTargetBanks(userId, accountId);
        return banks.stream()
                // 日付で昇順にソート
                .sorted(Comparator.comparing(BankAccountMonthlyModel::getTargetYm))
                .collect(Collectors.toMap(
                        BankAccountMonthlyModel::getTargetYm,    // キーとして日付をフォーマット
                        BankAccountMonthlyModel::getBalance,              // 値として価格
                        (existing, replacement) -> existing,              // キーが重複した場合に既存の値を保持
                        LinkedHashMap::new                                // ソート順を保持するためにLinkedHashMapを使用
                ));
    }

    @Override
    public List<BankAccountMonthlyModel> retrieveBankName(String userId) {
        return bankAccountMonthlyMapper.retrieveBankName(userId);
    }

    /** 更新 */
    @Override
    public int[] updateBankAccountMonthly(String targetYm, String userId, Integer accountId, Integer balance) {
        return bankAccountMonthlyMapper.updateBankAccountMonthly(targetYm, userId, accountId, balance);
    }

}
