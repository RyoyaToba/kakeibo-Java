package com.demo.utils;

import com.demo.entity.Item;
import com.demo.form.SpendingForm;

import java.util.Date;

public class ItemUtils {

    /**
     *
     * @param form
     * @return
     */
    public static Item formToItem(SpendingForm form) {

        Item item = new Item();
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setCategoryId(form.getCategoryId());
        item.setTargetDate(DateUtils.convertStringToDate(form.getTargetDate()));
        item.setCreatedBy("T00001");
        item.setCreatedDate(new Date());
        item.setUpdatedBy("T00001");
        item.setUpdatedDate(new Date());
        return item;
    }





}
