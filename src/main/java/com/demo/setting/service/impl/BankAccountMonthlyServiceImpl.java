package com.demo.setting.service.impl;

import com.demo.setting.model.BankAccountMonthlyModel;
import com.demo.setting.repository.BankAccountMonthlyMapper;
import com.demo.setting.service.BankAccountMonthlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    /** 更新 */
    @Override
    public int[] updateBankAccountMonthly(String targetYm, String userId, Integer accountId, Integer balance) {
        return bankAccountMonthlyMapper.updateBankAccountMonthly(targetYm, userId, accountId, balance);
    }

}
