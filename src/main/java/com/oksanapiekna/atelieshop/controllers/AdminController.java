package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.service.ProductService;
import org.hibernate.annotations.FetchProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;

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
        model.addAttribute("products",productService.findAll());
        return "admin/products";
    }

    @PostMapping("/addProduct")
    public String addProducts(@RequestParam MultipartFile multipartFile, Product product){
        productService.save(product,multipartFile);
        return "redirect:/admin/products";
    }

    @PostMapping("/changeStatusOfProduct")
    public String changeStatusOfProduct(int productId,boolean switchValue){
        productService.changeStatus(productId,switchValue);
        return "redirect:/admin/products";
    }

    @PostMapping("/deleteProductById/{productId}")
    public String deleteProductById(@PathVariable int productId){
        productService.deleteByID(productId);
        return "redirect:/admin/products";
    }

}
