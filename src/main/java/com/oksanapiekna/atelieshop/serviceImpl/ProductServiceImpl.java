package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.*;
import com.oksanapiekna.atelieshop.jpa.PageableProductJPA;
import com.oksanapiekna.atelieshop.jpa.ProductJPA;
import com.oksanapiekna.atelieshop.service.ProductService;
import com.oksanapiekna.atelieshop.service.SizeService;
import com.oksanapiekna.atelieshop.service.TypeOfProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductJPA productJPA;
    private final PageableProductJPA pageableProductJPA;
    private final TypeOfProductService typeOfProductService;
    private final SizeService sizeService;

    @Autowired
    public ProductServiceImpl(ProductJPA productJPA, PageableProductJPA pageableProductJPA, TypeOfProductService typeOfProductService, SizeService sizeService) {
        this.productJPA = productJPA;
        this.pageableProductJPA = pageableProductJPA;
        this.typeOfProductService = typeOfProductService;
        this.sizeService = sizeService;
    }

    @Override
    public Product save(Product product) {
        return productJPA.save(product);
    }

    @Override
    public Product save(Product product, MultipartFile multipartFile) {
        if(multipartFile!=null && product!=null){
            try {
                product.setSeason(product.getSeason().replace(",",""));
                product.setImg(multipartFile.getBytes());
                product.setImgName(multipartFile.getOriginalFilename());
                product.setImgType(multipartFile.getContentType());
                return productJPA.save(product);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public Product update(Product product, MultipartFile multipartFile) {
        System.out.println(product);
        if(product!=null){
            Product productDB = productJPA.findById(product.getId()).orElse(new Product());

            productDB.setName(product.getName());
            productDB.setArticle(product.getArticle());
            productDB.setDescription(product.getDescription());
            productDB.setCategory(product.getCategory());
            productDB.setSeason(product.getSeason().replace(",",""));
            productDB.setPrice(product.getPrice());
            productDB.setTypeOfProduct(product.getTypeOfProduct());
            productDB.setSizes(product.getSizes());
            System.out.println("Mulipart "+multipartFile.getSize());
            if(multipartFile.getSize()>0){
                try {
                    productDB.setImg(multipartFile.getBytes());
                    productDB.setImgName(multipartFile.getOriginalFilename());
                    productDB.setImgType(multipartFile.getContentType());
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            return productJPA.save(productDB);
        }
        return null;
    }

    @Override
    public Product findById(int id) {
        return productJPA.getOne(id);
    }

    @Override
    public Product changeStatus(int id, boolean status) {
        if(id>0){
            Product product = productJPA.getOne(id);
            if(status){
                product.setStatusOfEntity(StatusOfEntity.ACTIVE);
            }else {
                product.setStatusOfEntity(StatusOfEntity.ARCHIVED);
            }
            return productJPA.save(product);
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        return productJPA.findAll();
    }

    @Override
    public List<Product> findByStatus(StatusOfEntity status) {
        return productJPA.findByStatus(status);
    }

    @Override
    public List<Product> getFilteredProducts(String category, List<Integer> types,List<String> seasons,List<Integer> sizes, double maxPrice,double minPrice, String sortType) {
        System.out.println(productJPA.findBySizesId(24));
        Category categoryEnum;
        System.out.println("category " + category);
        if(category.toLowerCase().equals("clothes"))
        {
            categoryEnum = Category.CLOTHES;
        }else{
            categoryEnum = Category.SHOES;
        }
        if(types.isEmpty()){
            types = typeOfProductService.findByCategory(categoryEnum).stream().map(TypeOfProduct::getId).collect(Collectors.toList());
        }
        if(sizes.isEmpty()){
            sizes = sizeService.findByCategory(categoryEnum).stream().map(Size::getId).collect(Collectors.toList());
        }
        if(seasons.isEmpty()){
            seasons = new ArrayList<>();
            seasons.add("summer");
            seasons.add("demiseason");
        }
        System.out.println(types);
        System.out.println(sizes);
        System.out.println(seasons);

//        List<Product> products = new ArrayList<>(pageableProductJPA.getFilterProduct(StatusOfEntity.ACTIVE, types, seasons,sizes, PageRequest.of(0, 12)));
        List<Product> products = new ArrayList<>(pageableProductJPA.findByStatusOfEntityAndTypeOfProductIdInAndSeasonInAndSizesIdInOrderByIdDesc(StatusOfEntity.ACTIVE, types,seasons,sizes));
        System.out.println(products);
        return products;
    }

    @Override
    public void deleteByID(int id) {
        productJPA.deleteById(id);
    }
}
