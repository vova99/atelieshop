package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
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
            productDB.setSeason(product.getSeason());
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
    public List<Product> getFilteredProducts(String category, int[] types, String[] seasons, String[] sizes, double maxPrice,double minPrice, String sortType) {
        Category categoryEnum;
        if(category.toLowerCase().equals("clothes"))
        {
            categoryEnum = Category.CLOTHES;
        }else{
            categoryEnum = Category.SHOES;
        }
        if(types==null){
            types = typeOfProductService.findByCategory(categoryEnum).stream().flatMapToInt(typeOfProduct -> IntStream.of(typeOfProduct.getId())).toArray();
        }
//        if(sizes.length==0){
//            sizes = sizeService.findByCategory(categoryEnum).stream().flatMapToInt(typeOfProduct -> IntStream.of(typeOfProduct.getId())).toArray();
//        }
        if(seasons==null){
            seasons = new String[1];
            seasons[0] = "summer";
        }
        System.out.println(types.length);
        System.out.println(seasons.length);

        List<Product> products = pageableProductJPA.getFilterProduct(StatusOfEntity.ACTIVE,types,seasons,maxPrice,minPrice, PageRequest.of(0,1));
        return products;
    }

    @Override
    public void deleteByID(int id) {
        productJPA.deleteById(id);
    }
}
