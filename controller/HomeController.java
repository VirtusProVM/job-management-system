package com.jobentry.controller;

import com.jobentry.entity.Category;
import com.jobentry.entity.Jobs;
import com.jobentry.service.CategoryService;
import com.jobentry.service.JobsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final JobsService jobsService;

    @Autowired
    public HomeController(CategoryService categoryService, JobsService jobsService) {
        this.categoryService = categoryService;
        this.jobsService = jobsService;
    }

    @GetMapping("/")
    public String homepage(Model model) {

        List<Category> categories = categoryService.getAll();

        model.addAttribute("categories", categories);

        List<Jobs> jobsList = jobsService.getAll();

        model.addAttribute("jobs", jobsList);

        List<Jobs> fulltime = jobsService.getFulltimeJobs();

        model.addAttribute("fulltime", fulltime);

        List<Jobs> parttime = jobsService.getParttimeJobs();

        model.addAttribute("parttime", parttime);

        List<Jobs> remote = jobsService.getRemoteJobs();

        model.addAttribute("remote", remote);

        return "index";
    }
}
