package com.oksanapiekna.atelieshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/")
    public String getAdminIndex(Model model){
        return "index-analytics";
    }

    @GetMapping("/products")
    public String getAdminProducts(Model model){
        return "admin-products";
    }
}
