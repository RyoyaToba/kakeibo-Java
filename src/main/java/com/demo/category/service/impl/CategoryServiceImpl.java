package com.demo.category.service.impl;

import com.demo.category.entity.Category;
import com.demo.category.model.CategoryUI;
import com.demo.category.repository.CategoryMapper;
import com.demo.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /** 新規作成 */
    @Override
    public void insert(Category category) {
        categoryMapper.insert(category);
    }

    /** 全件取得 */
    @Override
    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }

    /** UIベースに変換する */
    @Override
    public List<CategoryUI> convertCategoryToCategoryUI(List<Category> categories) {
        return categories.stream()
                .map(category -> {
                        CategoryUI categoryUI = new CategoryUI();
                        categoryUI.setId(category.getId());
                        categoryUI.setName(category.getName());
                        return categoryUI;
                })
                .collect(Collectors.toList());
    }

    /** 削除 */
    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }

}
