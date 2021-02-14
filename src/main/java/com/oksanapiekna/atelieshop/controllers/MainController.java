package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class MainController {
    private ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    public void checkCheckCookie(String id,HttpServletResponse httpServletResponse){
        if(id.isEmpty()){
            UUID uuid = UUID.randomUUID();
            httpServletResponse.addCookie(new Cookie("id", uuid.toString()));
        }
    }

    @GetMapping("/")
    public String getIndex(@CookieValue(value = "id", defaultValue = "") String username,Model model, HttpServletResponse httpServletResponse)  {
        checkCheckCookie(username,httpServletResponse);
        model.addAttribute("activeLink","index");
        return "index";
    }

    @GetMapping("/shop")
    public String getShop(@CookieValue(value = "id", defaultValue = "") String username, Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse);
        System.out.println(username);
        model.addAttribute("products",productService.findAll());
        model.addAttribute("activeLink","shop");
        return "shop-grid";
    }

    @GetMapping("/contacts")
    public String getContact(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse);
        model.addAttribute("activeLink","contacts");
        return "contacts";
    }

    @GetMapping("/cart")
    public String getCart(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse);
        model.addAttribute("activeLink","cart");
        return "cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse);
        model.addAttribute("activeLink","cart");
        return "checkout";
    }

    @GetMapping("/pricing")
    public String getPricing(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse);
        model.addAttribute("activeLink","pricing");
        return "pricing";
    }

    @GetMapping("/singleProduct/{id}")
    public String getSingleProduct(@PathVariable int id, Model model){
        model.addAttribute("product",productService.findById(id));
        model.addAttribute("allProducts",productService.findAll());
        model.addAttribute("activeLink","singleProduct");
        return "single-product";
    }
}
