package com.oksanapiekna.atelieshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping ("/")
    public String getIndex(Model model){
        return "index_new";
    }

    @GetMapping ("/delivery&payment")
    public String getPayment(Model model){
        return "pricing";
    }

    @GetMapping ("/contacts")
    public String getContacts(Model model){
        return "contacts";
    }



}
