package com.jobentry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @Autowired
    public AboutController() {}

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
