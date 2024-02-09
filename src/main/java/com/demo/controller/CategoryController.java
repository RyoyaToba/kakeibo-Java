package com.demo.controller;

import com.demo.entity.Category;
import com.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    /**
     * category Page
     * @return category.index
     */
    @RequestMapping("")
    public String categoryPage(){
        return "category";
    }


    /**
     * カテゴリの新規作成
     * @param name
     * @return
     */
    @RequestMapping("/newCreate")
    public String newCreate(String name) {

        Category category = new Category();
        category.setName(name);
        category.setCreatedBy("T00001");
        category.setCreatedDate(new Date());
        category.setUpdatedBy("T00001");
        category.setUpdatedDate(new Date());

        categoryService.insert(category);

        return "redirect:/category";
    }

}
