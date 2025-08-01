
package com.demo.home.model;

import java.util.List;

import com.demo.item.model.ItemUI;
import com.demo.setting.model.Withdrawal;
import com.demo.category.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomePageViewModel {

    private List<Category> categories;
    private List<ItemUI> items;
    private boolean existsItems;
    private List<String> months;
    private String titleMonth;
    private List<Withdrawal> withdrawals;
    private Integer previousMonthSum;
    
}
