package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.Size;
import com.oksanapiekna.atelieshop.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class SizeProductController {
    @Autowired
    private final SizeService sizeService;

    public SizeProductController(SizeService sizeService) {
        this.sizeService = sizeService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sizes")
    public String getAll(Model model,String categoryName){
        model.addAttribute("sizes",sizeService.findAll());
        if(categoryName==null) {
            model.addAttribute("tabName", "CLOTHES");
        }else {
            model.addAttribute("tabName", categoryName);
        }
        return "/admin/sizes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addSize")
    public String addType(String categoryName, String nameOfType){
        sizeService.create(nameOfType,categoryName);
        return "redirect:/admin/sizes?categoryName="+categoryName;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editSize")
    public String editType(Size size){
        size = sizeService.update(size);
        return "redirect:/admin/sizes?categoryName="+size.getCategory().name();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteSize")
    public String deleteType(int typeId){
        String name = sizeService.findById(typeId).getCategory().name();
        sizeService.deleteByID(typeId);
        return "redirect:/admin/sizes?categoryName="+name;
    }
}
