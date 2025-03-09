package com.jobentry.controller;

import com.jobentry.entity.Category;
import com.jobentry.entity.Jobs;
import com.jobentry.service.CategoryService;
import com.jobentry.service.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CategoryController {

    private CategoryService categoryService;
    private JobsService jobsService;

    @Autowired
    public CategoryController(CategoryService categoryService, JobsService jobsService) {
        this.categoryService = categoryService;
        this.jobsService = jobsService;
    }

    @GetMapping("/category")
    public String categoryList(Model model) {

        List<Category> categories = categoryService.getAll();

        model.addAttribute("categories", categories);


        return "category";
    }

    @GetMapping("/category/{id}")
    public String viewCategory(@PathVariable("id") int id, Model model) {

        List<Jobs> jobsList = jobsService.getJobsByCategory(id);

        model.addAttribute("jobList", jobsList);


        return "view-category";
    }


}
