package com.demo.payment.service.impl;

import com.demo.master.entity.AccountTypeMaster;
import com.demo.master.repository.AccountTypeMasterMapper;
import com.demo.payment.entity.BankAccount;
import com.demo.payment.model.BankAccountUI;
import com.demo.payment.repository.PaymentMapper;
import com.demo.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private AccountTypeMasterMapper accountTypeMasterMapper;

    /** 口座登録 */
    public void insert(BankAccount account) {
        paymentMapper.insert(account);
    }

    /** 口座全件取得 */
    @Override
    public List<BankAccount> loadByUserId(String userId) {
        return paymentMapper.loadByUserId(userId);
    }

    /** UIベースに変換 */
    public List<BankAccountUI> convertAccountToAccountUI(List<BankAccount> accounts) {

        List<AccountTypeMaster> masters = accountTypeMasterMapper.selectAll();

        return accounts.stream()
                       .map(account -> {
                            BankAccountUI accountUI = new BankAccountUI();
                            accountUI.setAccountId(account.getAccountId());
                            accountUI.setAccountName(account.getName());
                            accountUI.setBalance(account.getBalance());
                            masters.stream()
                                    .filter(m -> m.getId().equals(account.getType()))
                                    .findFirst()
                                    .ifPresent(m -> accountUI.setAccountTypeName(m.getName()));

                            return accountUI;
                       })
                        .collect(Collectors.toList());
    }

}
