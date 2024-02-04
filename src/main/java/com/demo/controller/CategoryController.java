package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    /**
     * category Page
     * @return category.index
     */
    @RequestMapping("")
    public String categoryPage(){
        return "category";
    }

}
