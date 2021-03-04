package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.TypeOfProduct;
import com.oksanapiekna.atelieshop.service.TypeOfProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TypeOfProductController {
    @Autowired
    private TypeOfProductService typeOfProductService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/categories")
    public String getAll(Model model,String categoryName){
        model.addAttribute("types",typeOfProductService.findAll());
        if(categoryName==null) {
            model.addAttribute("tabName", "CLOTHES");
        }else {
            model.addAttribute("tabName", categoryName);
        }
        return "/admin/categories";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addType")
    public String addType(String categoryName, String nameOfType){
        typeOfProductService.create(nameOfType,categoryName);
        return "redirect:/admin/categories?categoryName="+categoryName;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editType")
    public String editType(TypeOfProduct type){
        TypeOfProduct typeOfProduct = typeOfProductService.update(type);
        return "redirect:/admin/categories?categoryName="+typeOfProduct.getCategory().name();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteType")
    public String deleteType(int typeId){
        String name = typeOfProductService.findById(typeId).getCategory().name();
        typeOfProductService.deleteByID(typeId);
        return "redirect:/admin/categories?categoryName="+name;
    }
}
