package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.Size;
import com.oksanapiekna.atelieshop.service.SizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class SizeProductController {
    private final SizeService sizeService;

    @GetMapping("/sizes")
    private String getAll(Model model,String categoryName){
        model.addAttribute("sizes",sizeService.findAll());
        if(categoryName==null) {
            model.addAttribute("tabName", "CLOTHES");
        }else {
            model.addAttribute("tabName", categoryName);
        }
        return "/admin/sizes";
    }

    @PostMapping("/addSize")
    private String addType(String categoryName, String nameOfType){
        sizeService.create(nameOfType,categoryName);
        return "redirect:/admin/sizes?categoryName="+categoryName;
    }

    @PostMapping("/editSize")
    private String editType(Size size){
        size = sizeService.update(size);
        return "redirect:/admin/sizes?categoryName="+size.getCategory().name();
    }

    @PostMapping("/deleteSize")
    private String deleteType(int typeId){
        String name = sizeService.findById(typeId).getCategory().name();
        sizeService.deleteByID(typeId);
        return "redirect:/admin/sizes?categoryName="+name;
    }
}
