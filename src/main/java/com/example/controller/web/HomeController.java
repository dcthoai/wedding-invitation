package com.example.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping(value = "/")
    @ResponseBody
    public String homepage() {
        String currentWorkingDirectory = System.getProperty("user.dir");
        return "Current working directory: " + currentWorkingDirectory;
    }
}
