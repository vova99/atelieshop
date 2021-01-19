package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.service.ProductService;
import com.oksanapiekna.atelieshop.service.TypeOfProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private TypeOfProductService typeOfProductService;

    @GetMapping("/")
    public String getAdminIndex(Model model){
        return "index-analytics";
    }

    @GetMapping("/products")
    public String getAdminProducts(Model model){
        model.addAttribute("types",typeOfProductService.findAll());
        model.addAttribute("products",productService.findAll());
        return "admin/products";
    }

    @PostMapping("/addProduct")
    public String addProducts(@RequestParam MultipartFile multipartFile, Product product,String[] sizes){
        productService.save(product,multipartFile,sizes);
        return "redirect:/admin/products";
    }

    @PostMapping("/editProduct")
    public String editProduct(@RequestParam MultipartFile multipartFile, Product product,String[] sizes){
        productService.update(product,multipartFile,sizes);
        return "redirect:/admin/products";
    }

    @PostMapping("/changeStatusOfProduct")
    public String changeStatusOfProduct(int productId,boolean switchValue){
        productService.changeStatus(productId,switchValue);
        return "redirect:/admin/products";
    }

    @PostMapping("/deleteProductById")
    public String deleteProductById(int productId){
        productService.deleteByID(productId);
        return "redirect:/admin/products";
    }

}
