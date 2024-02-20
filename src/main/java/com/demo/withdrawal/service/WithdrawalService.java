package com.demo.withdrawal.service;

import com.demo.item.entity.Item;
import com.demo.withdrawal.Model.Withdrawal;

import java.util.List;
import java.util.Map;

public interface WithdrawalService {

    /** itemの金額を引き落とし口座ごとに集計する */
    Map<Integer, Integer> calcSumprice(List<Item> items);

    /**  */
    List<Withdrawal> createWithdrawal(Map<Integer, Integer> map);

}
