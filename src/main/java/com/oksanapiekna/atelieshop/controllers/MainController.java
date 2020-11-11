package com.oksanapiekna.atelieshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("activeLink","index");
        return "index";
    }

    @GetMapping("/shop")
    public String getShop(Model model){
        model.addAttribute("activeLink","shop");
        return "shop-grid";
    }
    @GetMapping("/contacts")
    public String getContact(Model model){
        model.addAttribute("activeLink","contacts");
        return "contacts";
    }

    @GetMapping("/cart")
    public String getCart(Model model){
        model.addAttribute("activeLink","cart");
        return "cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model){
        model.addAttribute("activeLink","cart");
        return "checkout";
    }

    @GetMapping("/pricing")
    public String getPricing(Model model){
        model.addAttribute("activeLink","pricing");
        return "pricing";
    }

    @GetMapping("/singleProduct")
    public String getSingleProduct(Model model){
        model.addAttribute("activeLink","singleProduct");
        return "single-product";
    }

}
