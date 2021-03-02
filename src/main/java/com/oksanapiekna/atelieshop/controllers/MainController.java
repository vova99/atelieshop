package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.OrderInfo;
import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.service.*;
import com.oksanapiekna.atelieshop.viberBot.ViberListenerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class MainController {
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderDetailsService orderDetailsService;
    private final TypeOfProductService typeOfProductService;
    private final SizeService sizeService;
    private final ViberListenerService viberListenerService;


    public void checkCheckCookie(String id,HttpServletResponse httpServletResponse,Model model){
        if(!id.isEmpty()) {
            OrderInfo  orderInfo = orderService.findById(UUID.fromString(id));
            if(orderInfo!=null) {
                model.addAttribute("order", orderInfo);
                return;
            }
        }
        OrderInfo order = new OrderInfo();
        order = orderService.save(order);
        httpServletResponse.addCookie(new Cookie("id", order.getId().toString()));
        model.addAttribute("order",order);
    }

    @GetMapping("/")
    public String getIndex(@CookieValue(value = "id", defaultValue = "") String username,Model model, HttpServletResponse httpServletResponse)  {
        checkCheckCookie(username,httpServletResponse,model);
        model.addAttribute("activeLink","index");
        return "index";
    }

    @GetMapping("/shop")
    public String getShop(@CookieValue(value = "id", defaultValue = "") String username, Model model,HttpServletResponse httpServletResponse,String category){
        checkCheckCookie(username,httpServletResponse,model);
        System.out.println(username);
        List<Product> products = productService.findByStatus(StatusOfEntity.ACTIVE);
        if(category!=null && category.toLowerCase().equals("clothes")) {
            model.addAttribute("types", typeOfProductService.findByCategory(Category.CLOTHES));
            model.addAttribute("sizes", sizeService.findByCategory(Category.CLOTHES));
            model.addAttribute("products", productService.findByStatus(StatusOfEntity.ACTIVE));
        }else{
            model.addAttribute("types", typeOfProductService.findByCategory(Category.SHOES));
            model.addAttribute("sizes", sizeService.findByCategory(Category.SHOES));
            model.addAttribute("products", productService.findByStatus(StatusOfEntity.ACTIVE));
        }
        Product maxPrice = products.stream().max((o1, o2) -> (int) (o1.getPrice() - o2.getPrice())).orElse(new Product());
        Product minPrice = products.stream().min((o1, o2) -> (int) (o1.getPrice() - o2.getPrice())).orElse(new Product());
        model.addAttribute("maxPrice",maxPrice.getPrice());
        model.addAttribute("minPrice",minPrice.getPrice());
        model.addAttribute("activeLink","shop");
        return "shop-grid";
    }

    @GetMapping("/contacts")
    public String getContact(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse){
        checkCheckCookie(username,httpServletResponse,model);
        model.addAttribute("activeLink","contacts");
        return "contacts";
    }
    @PostMapping("/contacts")
    public String getContact(String name, String phone, String description){
        viberListenerService.sendForAll(name,phone,description);
        return "redirect:/";
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

    @PostMapping("/checkout")
    public String postCheckout(@RequestParam List<Integer> count,@RequestParam List<Integer> id){
        orderDetailsService.save(id,count);
        return "redirect:/checkout";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(@CookieValue(value = "id", defaultValue = "") String username,Model model,HttpServletResponse httpServletResponse,
                              String name, String phone, String description){
        checkCheckCookie(username,httpServletResponse,model);
        OrderInfo order = (OrderInfo) model.getAttribute("order");
        order = orderService.submitOrder(order,name,phone,description);

        viberListenerService.sendForAll(order);

        Cookie cookie = new Cookie("id","");
        httpServletResponse.addCookie(cookie);
        return "redirect:/";
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
