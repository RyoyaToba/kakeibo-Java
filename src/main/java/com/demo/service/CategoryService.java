package com.demo.service;

import com.demo.entity.Category;

import java.util.List;

public interface CategoryService {

    /** 新規作成 */
    void insert(Category category);

    /** 全件取得 */
    List<Category> selectAll();

}
