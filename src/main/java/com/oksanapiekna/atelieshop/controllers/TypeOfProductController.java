package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.TypeOfProduct;
import com.oksanapiekna.atelieshop.service.TypeOfProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TypeOfProductController {
    private TypeOfProductService typeOfProductService;


    public TypeOfProductController(TypeOfProductService typeOfProductService) {
        this.typeOfProductService = typeOfProductService;
    }


    @GetMapping("/categories")
    private String getAll(Model model,String categoryName){
        model.addAttribute("types",typeOfProductService.findAll());
        if(categoryName==null) {
            model.addAttribute("tabName", "CLOTHES");
        }else {
            model.addAttribute("tabName", categoryName);
        }
        return "/admin/categories";
    }

    @PostMapping("/addType")
    private String addType(String categoryName, String nameOfType){
        typeOfProductService.create(nameOfType,categoryName);
        return "redirect:/admin/categories?categoryName="+categoryName;
    }

    @PostMapping("/editType")
    private String editType(TypeOfProduct type){
        TypeOfProduct typeOfProduct = typeOfProductService.update(type);
        return "redirect:/admin/categories?categoryName="+typeOfProduct.getCategory().name();
    }

    @PostMapping("/deleteType")
    private String deleteType(int typeId){
        String name = typeOfProductService.findById(typeId).getCategory().name();
        typeOfProductService.deleteByID(typeId);
        return "redirect:/admin/categories?categoryName="+name;
    }
}
