package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.OrderDetails;
import com.oksanapiekna.atelieshop.entity.OrderInfo;
import com.oksanapiekna.atelieshop.service.OrderDetailsService;
import com.oksanapiekna.atelieshop.service.OrderService;
import com.oksanapiekna.atelieshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class MainController {
    private ProductService productService;
    private OrderService orderService;
    private OrderDetailsService orderDetailsService;


    public void checkCheckCookie(String id,HttpServletResponse httpServletResponse,Model model){
        if(id.isEmpty()){
            OrderInfo order = new OrderInfo();
            order = orderService.save(order);
            httpServletResponse.addCookie(new Cookie("id", order.getId().toString()));
            model.addAttribute("order",order);
        }else {
            model.addAttribute("order",orderService.findById(UUID.fromString(id)));
        }
    }

    @GetMapping("/")
    public String getIndex(@CookieValue(value = "id", defaultValue = "") String username,Model model, HttpServletResponse httpServletResponse)  {
        checkCheckCookie(username,httpServletResponse,model);
        model.addAttribute("activeLink","index");
        return "index";
    }

    @GetMapping("/shop")
    public String getShop(@CookieValue(value = "id", defaultValue = "") String username, Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse,model);
        System.out.println(username);
        model.addAttribute("products",productService.findAll());
        model.addAttribute("activeLink","shop");
        return "shop-grid";
    }

    @GetMapping("/contacts")
    public String getContact(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse,model);
        model.addAttribute("activeLink","contacts");
        return "contacts";
    }

    @GetMapping("/cart")
    public String getCart(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse,model);
        model.addAttribute("activeLink","cart");
        return "cart";
    }

    @GetMapping("/addToCart/{productId}")
    public String addToCart(@PathVariable int productId, @CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse,model);
        OrderInfo order = (OrderInfo) model.getAttribute("order");
        orderDetailsService.addProductToOrder(order.getId(),productId);
        return "redirect:/shop";
    }

    @GetMapping("/deleteFromCart/{productId}")
    public String deleteFromCart(@PathVariable int productId, @CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse,model);
        OrderInfo order = (OrderInfo) model.getAttribute("order");
        orderDetailsService.deleteProductFromOrder(order.getId(),productId);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse,model);
        model.addAttribute("activeLink","cart");
        return "checkout";
    }

    @GetMapping("/pricing")
    public String getPricing(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse,model);
        model.addAttribute("activeLink","pricing");
        return "pricing";
    }

    @GetMapping("/singleProduct/{id}")
    public String getSingleProduct(@PathVariable int id,@CookieValue(value = "id", defaultValue = "") String username, Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse,model);
        model.addAttribute("product",productService.findById(id));
        model.addAttribute("allProducts",productService.findAll());
        model.addAttribute("activeLink","singleProduct");
        return "single-product";
    }
}
