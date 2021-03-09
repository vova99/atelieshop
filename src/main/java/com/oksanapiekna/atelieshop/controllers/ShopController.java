package com.oksanapiekna.atelieshop.controllers;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.entity.dto.ProductDTO;
import com.oksanapiekna.atelieshop.service.ProductService;
import com.oksanapiekna.atelieshop.service.SizeService;
import com.oksanapiekna.atelieshop.service.TypeOfProductService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class ShopController {
    private final MainController mainController;
    private final TypeOfProductService typeOfProductService;
    private final SizeService sizeService;
    private final ProductService productService;

    @GetMapping("/shop")
    public String getShop(@CookieValue(value = "id", defaultValue = "") String username, Model model, HttpServletResponse httpServletResponse, String category){
        mainController.checkCheckCookie(username,httpServletResponse,model);
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
        model.addAttribute("category",category);
        model.addAttribute("activeLink","shop");
        return "shop-grid";
    }

    @ResponseBody
    @PostMapping("/shop")
    public JSONObject getFilteredProducts(String category, Integer[] types, String[] seasons, Integer[] sizes,
                                          double maxPrice, double minPrice, String sortType, Integer page, Integer size){
        if(types==null){types = new Integer[0];}
        if(seasons==null){seasons = new String[0];}
        if(sizes==null){sizes = new Integer[0];}
        List<Product> products = productService.getFilteredProducts(category, Arrays.asList(types),Arrays.asList(seasons),Arrays.asList(sizes),maxPrice,minPrice,sortType,page,size);
//        List<Product> products = productService.findAll();
        int countOfAllProducts =  productService.getCountOfElements(category,Arrays.asList(types),Arrays.asList(seasons),Arrays.asList(sizes),maxPrice,minPrice,sortType,page,size);
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Product product:products){
            jsonArray.add(ProductDTO.convertToDTO(product));
        }

        double countOfElements = countOfAllProducts;

        object.put("products",jsonArray);
        object.put("countOfAllProducts",countOfAllProducts);
        object.put("pageCount",Math.ceil(countOfElements/ size));
        return object;
    }

}
