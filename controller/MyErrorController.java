package com.jobentry.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public String clientError() {

        return "404";
    }

    public String getErrorPath() {

        return PATH;
    }

}
