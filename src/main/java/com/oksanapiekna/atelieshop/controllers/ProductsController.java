package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ProductsController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getShop(Model model){
        model.addAttribute("activeLink","shop");
        return "shop-grid";
    }

    @GetMapping("/getImgByProductId/{productId}")
    public ResponseEntity<ByteArrayResource> getImgByProductId(@PathVariable int productId){
        Product doc = productService.findById(productId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getImgType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getImgName()+"\"")
                .body(new ByteArrayResource(doc.getImg()));
    }

    @GetMapping("/getProductById/{productId}")
    public String getProductById(@PathVariable int productId, Model model){
        model.addAttribute("activeLink","singleProduct");
        model.addAttribute("product",productService.findById(productId));
        return "single-product";
    }
}
