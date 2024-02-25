package com.demo.category.controller;

import com.demo.category.entity.Category;
import com.demo.category.form.CategoryForm;
import com.demo.category.model.CategoryUI;
import com.demo.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    private CategoryForm setCategoryForm(){
        return new CategoryForm();
    }

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
     * @param categoryForm 入力値を受け取るフォーム
     * @return カテゴリページへのリダイレクト
     */
    @RequestMapping("/newCreate")
    public String newCreate(
            @Validated CategoryForm categoryForm
            ,BindingResult result
            ,Model model
    ) {

        if (result.hasErrors()){
            return categoryPage(model);
        }

        Category category = new Category();
        category.setName(categoryForm.getName());
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
