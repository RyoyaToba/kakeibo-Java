package com.demo.category.service;

import com.demo.category.entity.Category;
import com.demo.category.model.CategoryUI;

import java.util.List;

public interface CategoryService {

    /** 新規作成 */
    void insert(Category category);

    /** 全件取得 */
    List<Category> selectAll();

    /** UIベースに変換する */
    List<CategoryUI> convertCategoryToCategoryUI(List<Category> categories);

    /** 削除 */
    void delete(Integer id);

}
