package com.oksanapiekna.atelieshop.entity.dto;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.Size;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductDTO {
    private int id;

    private String name;
    private String article;
    private String description;

    private double price;
    private String season;

    private List<String> sizes;
    private String typeOfProduct;
    private String category;

    public static ProductDTO convertToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setArticle(product.getArticle());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setSeason(product.getSeason());

        productDTO.setSizes(product.getSizes().stream().map(Size::getSize).collect(Collectors.toList()));
        productDTO.setTypeOfProduct(product.getTypeOfProduct().getName());
        productDTO.setCategory(product.getCategory().name());
        return productDTO;
    }

}
