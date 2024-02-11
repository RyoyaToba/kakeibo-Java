package com.demo.payment.service.impl;

import com.demo.master.entity.AccountTypeMaster;
import com.demo.master.repository.AccountTypeMasterMapper;
import com.demo.payment.entity.Account;
import com.demo.payment.model.AccountUI;
import com.demo.payment.repository.PaymentMapper;
import com.demo.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private AccountTypeMasterMapper accountTypeMasterMapper;

    /** 口座登録 */
    public void insert(Account account) {
        paymentMapper.insert(account);
    }

    /** 口座全件取得 */
    @Override
    public List<Account> selectAll() {
        return paymentMapper.selectAll();
    }

    /** UIベースに変換 */
    public List<AccountUI> convertAccountToAccountUI(List<Account> accounts) {

        List<AccountTypeMaster> masters = accountTypeMasterMapper.selectAll();

        return accounts.stream()
                       .map(account -> {
                            AccountUI accountUI = new AccountUI();
                            accountUI.setId(account.getId());
                            accountUI.setAccountName(account.getName());
                            accountUI.setBalance(account.getBalance());
                            masters.stream()
                                    .filter(m -> m.getId().equals(account.getType()))
                                    .findFirst()
                                    .ifPresent(m -> accountUI.setAccountTypeName(m.getName()));;

                            return accountUI;
                       })
                        .collect(Collectors.toList());
    }

}
