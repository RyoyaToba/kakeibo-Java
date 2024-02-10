package com.demo.category.controller;

import com.demo.category.entity.Category;
import com.demo.category.model.CategoryUI;
import com.demo.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

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
    public String categoryPage(Model model){

        // カテゴリの一覧表示
        List<CategoryUI> categoryUIs
                = categoryService.convertCategoryToCategoryUI(categoryService.selectAll());

        model.addAttribute("categoryUIs", categoryUIs);

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

    /**
     * カテゴリの削除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(Integer id) {

        categoryService.delete(id);

        return "redirect:/category";
    }






}
