package com.demo.category.service.impl;

import com.demo.category.entity.Category;
import com.demo.category.repository.CategoryMapper;
import com.demo.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

}
