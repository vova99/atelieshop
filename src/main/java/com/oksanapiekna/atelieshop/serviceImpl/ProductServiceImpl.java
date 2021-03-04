package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.*;
import com.oksanapiekna.atelieshop.jpa.PageableProductJPA;
import com.oksanapiekna.atelieshop.jpa.ProductJPA;
import com.oksanapiekna.atelieshop.service.ProductService;
import com.oksanapiekna.atelieshop.service.SizeService;
import com.oksanapiekna.atelieshop.service.TypeOfProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Product> findSameProducts(Product product) {
        List<Product> products = productJPA.findFirst10ByTypeOfProductId(product.getTypeOfProduct().getId());
        products.remove(product);
        if(products.size()<5){
            products.addAll(productJPA.findFirst5ByCategoryAndTypeOfProductIdIsNot(product.getCategory(),product.getTypeOfProduct().getId()));
        }
        return products;
    }

    public Category checkCategory(String category){
        if(category.toLowerCase().equals("clothes"))
        {
           return  Category.CLOTHES;
        }else{
            return  Category.SHOES;
        }
    }

    @Override
    public List<Product> getFilteredProducts(String category, List<Integer> types,List<String> seasons,
                                             List<Integer> sizes, double maxPrice,double minPrice,
                                             String sortType,int page,int size) {
        System.out.println(productJPA.findBySizesId(24));
        Category categoryEnum = checkCategory(category);

        if(types.isEmpty()){
            types = typeOfProductService.findByCategory(categoryEnum).stream().map(TypeOfProduct::getId).collect(Collectors.toList());
        }
        if(sizes.isEmpty()){
            sizes = sizeService.findByCategory(categoryEnum).stream().map(Size::getId).collect(Collectors.toList());
        }
        if(seasons.isEmpty()){
            seasons = new ArrayList<>();
            seasons.add("summer");
            seasons.add("winter");
            seasons.add("spring");
            seasons.add("autumn");
            seasons.add("demiseason");
        }
        System.out.println(types);
        System.out.println(sizes);
        System.out.println(seasons);
        System.out.println("max" + maxPrice);
        System.out.println("min" + minPrice);
        System.out.println("page" + page);
        System.out.println("size" + size);
//        List<Product> products = new ArrayList<>(pageableProductJPA.getFilterProduct(StatusOfEntity.ACTIVE, types, seasons,sizes, PageRequest.of(0, 12)));

        List<Product> products = new ArrayList<>(pageableProductJPA.findByStatusOfEntityAndTypeOfProductIdInAndSeasonInAndSizesIdInAndPriceLessThanEqualAndPriceGreaterThanEqualOrderByIdDesc(StatusOfEntity.ACTIVE, types,seasons,sizes,maxPrice,minPrice));
        Comparator<Product> productComparator;
        System.out.println("Sort type" + sortType);
        switch (sortType){
            case "highPrice":productComparator = (o1, o2) ->  (int) (o2.getPrice()-o1.getPrice());break;
            case "lowPrice":productComparator = (o1, o2) ->  (int) (o1.getPrice()-o2.getPrice());break;
            default:productComparator = (o1, o2) -> o2.getId()-o1.getId();
        }
        products.sort(productComparator);

        Pageable paging = PageRequest.of(page,size);
        int start = Math.min((int)paging.getOffset(), products.size());
        int end = Math.min((start + paging.getPageSize()), products.size());

        return products.subList(start,end);
    }

    @Override
    public int getCountOfElements(String category, List<Integer> types, List<String> seasons, List<Integer> sizes, double maxPrice, double minPrice, String sortType, int page, int size) {
        if(types.isEmpty()){
            types = typeOfProductService.findByCategory(checkCategory(category)).stream().map(TypeOfProduct::getId).collect(Collectors.toList());
        }
        if(sizes.isEmpty()){
            sizes = sizeService.findByCategory(checkCategory(category)).stream().map(Size::getId).collect(Collectors.toList());
        }
        if(seasons.isEmpty()){
            seasons = new ArrayList<>();
            seasons.add("summer");
            seasons.add("winter");
            seasons.add("spring");
            seasons.add("autumn");
            seasons.add("demiseason");
        }

        return pageableProductJPA.findByStatusOfEntityAndTypeOfProductIdInAndSeasonInAndSizesIdInAndPriceLessThanEqualAndPriceGreaterThanEqualOrderByIdDesc(StatusOfEntity.ACTIVE, types,seasons,sizes,maxPrice,minPrice).size();
  }


    @Override
    public void deleteByID(int id) {
        productJPA.deleteById(id);
    }
}
