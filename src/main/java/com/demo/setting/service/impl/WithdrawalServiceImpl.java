package com.demo.setting.service.impl;

import com.demo.item.entity.Item;
import com.demo.payment.entity.BankAccount;
import com.demo.payment.service.PaymentService;
import com.demo.setting.model.Withdrawal;
import com.demo.setting.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private PaymentService paymentService;

    /**
     * 引き落とし口座ごとに合計金額を算出する。
     * @param items
     * @return
     */
    @Override
    public Map<Integer, Integer> calcSumprice(List<Item> items) {
        return items.stream()
                .collect(Collectors.groupingBy(
                        Item::getBankSelectId,
                        Collectors.summingInt(Item::getPrice))
                );

    }

    /**  引き落としごとに計算された合計金額から、引き落としオブジェクトに変換する*/
    @Override
    public List<Withdrawal> createWithdrawal(Map<Integer, Integer> map, String userId) {
        // 登録している銀行口座を全件取得する
        List<BankAccount> accounts = paymentService.loadByUserId(userId);
        // 引き落としのオブジェクトを作成して返す
        return map.entrySet().stream()
                .map(entry -> {
                    Withdrawal withdrawal = new Withdrawal();
                    String accountName = accounts.stream()
                            .filter(account -> account.getAccountId().equals(entry.getKey()))
                            .map(BankAccount::getName)
                            .findFirst()
                            .orElse(null);

                    withdrawal.setBankName(accountName);
                    withdrawal.setSumPrice(entry.getValue());
                    return withdrawal;
                })
                .collect(Collectors.toList());
    }
}
