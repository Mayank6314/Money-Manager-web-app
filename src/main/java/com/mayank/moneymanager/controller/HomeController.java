package com.mayank.moneymanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/status", "/health"})
public class HomeController {

    // Good practice
    @GetMapping
    public String healthCheck(){
        return "Application is running";
    }
}
