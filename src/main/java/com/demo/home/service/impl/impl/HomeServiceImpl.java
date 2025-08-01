package com.demo.home.service.impl.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.category.entity.Category;
import com.demo.category.service.CategoryService;
import com.demo.common.service.CommonService;
import com.demo.common.utils.CommonUtils;
import com.demo.common.utils.DateUtils;
import com.demo.home.model.HomePageViewModel;
import com.demo.home.service.impl.HomeService;
import com.demo.item.entity.Item;
import com.demo.item.model.ItemUI;
import com.demo.item.service.ItemService;
import com.demo.setting.model.Withdrawal;
import com.demo.setting.service.WithdrawalService;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private CommonService commonService;
    
    /**
     * HomePageViewModel ホーム画面を作成するために必要な情報をまとめたModelを生成する
     * @param userId
     * @param targetDate
     * @param targetMonthStr
     * @return
     */
    public HomePageViewModel buildHomePage(String userId, String targetMonth) {

        String titleMonth;
        LocalDate targetDate;

        if (targetMonth == null) {
            targetDate = LocalDate.now();
            titleMonth = DateUtils.localDateToStringTitleMonth(targetDate);
        } else {
            targetDate = commonService.convertStringToFirstLocalDate(targetMonth);
            titleMonth = targetMonth;
        }
        
        List<Category> categories = categoryService.selectAll();
        LocalDate startDate = DateUtils.getStartOfMonth(targetDate);
        LocalDate endDate = DateUtils.getEndOfMonth(targetDate);

        List<Item> items = itemService.retrieveItemInTargetMonth(
                userId,
                DateUtils.convertLocalDateToDate(startDate),
                DateUtils.convertLocalDateToDate(endDate)
        );

        List<ItemUI> displayItems = itemService.convertItemToItemUI(items, categories);
        List<Withdrawal> withdrawals = withdrawalService.createWithdrawal(
                withdrawalService.calcSumprice(items),
                userId
        );

        return new HomePageViewModel(
                categories, 
                displayItems, 
                !items.isEmpty(), 
                CommonUtils.retrieveMonths(), 
                titleMonth, 
                withdrawals,
                null
        );
    }
}
