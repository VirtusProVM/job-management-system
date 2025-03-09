package com.jobentry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @Autowired
    public ContactController() {}

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
