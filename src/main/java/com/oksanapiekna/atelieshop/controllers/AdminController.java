package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getAdminIndex(Model model){
        return "index-analytics";
    }

    @GetMapping("/products")
    public String getAdminProducts(Model model){
        return "admin/products";
    }

    @PostMapping("/addProduct")
    public String addProducts(Product product){
        productService.save(product);
        return "redirect:/products";
    }
}
