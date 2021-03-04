package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.service.ProductService;
import com.oksanapiekna.atelieshop.service.SizeService;
import com.oksanapiekna.atelieshop.service.TypeOfProductService;
import com.oksanapiekna.atelieshop.service.ViberTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ViberTokenService viberTokenService;

    @Autowired
    private TypeOfProductService typeOfProductService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    @PostMapping("/")
    public String getAdminIndex(Model model){
        return "redirect:/admin/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/products")
    public String getAdminProducts(Model model){
        model.addAttribute("types",typeOfProductService.findAll());
        model.addAttribute("sizes",sizeService.findAll());
        model.addAttribute("products",productService.findAll());
        return "admin/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products")
    public String getAdminProducts(){
        return "redirect:/admin/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viberInfo")
    public String getViberInfo(Model model){
        model.addAttribute("viberToken",viberTokenService.getToken());
        return "admin/viber";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getNewViberToken")
    public String getNewToken(){
        viberTokenService.generateNewToken();
        return  "redirect:/admin/viberInfo";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public String addProducts(@RequestParam MultipartFile multipartFile, Product product){
        productService.save(product,multipartFile);
        return "redirect:/admin/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editProduct")
    public String editProduct(@RequestParam MultipartFile multipartFile, Product product){
        productService.update(product,multipartFile);
        return "redirect:/admin/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/changeStatusOfProduct")
    public String changeStatusOfProduct(int productId,boolean switchValue){
        productService.changeStatus(productId,switchValue);
        return "redirect:/admin/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteProductById")
    public String deleteProductById(int productId){
        productService.deleteByID(productId);
        return "redirect:/admin/products";
    }

}
