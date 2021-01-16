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
        model.addAttribute("products",productService.findByStatus(StatusOfEntity.ACTIVE));
        return "admin/products";
    }

    @PostMapping("/addProduct")
    public String addProducts(@RequestParam MultipartFile multipartFile, Product product){
        productService.save(product,multipartFile);
        return "redirect:/admin/products";
    }

    @GetMapping("/getImgByProductId/{productId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable int productId){
        Product doc = productService.findById(productId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getImgType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getImgName()+"\"")
                .body(new ByteArrayResource(doc.getImg()));
    }
}
