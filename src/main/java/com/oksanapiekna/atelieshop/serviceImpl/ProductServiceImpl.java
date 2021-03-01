package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.jpa.PageableProductJPA;
import com.oksanapiekna.atelieshop.jpa.ProductJPA;
import com.oksanapiekna.atelieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductJPA productJPA;
    private final PageableProductJPA pageableProductJPA;

    @Autowired
    public ProductServiceImpl(ProductJPA productJPA, PageableProductJPA pageableProductJPA) {
        this.productJPA = productJPA;
        this.pageableProductJPA = pageableProductJPA;
    }

    @Override
    public Product save(Product product) {
        return productJPA.save(product);
    }

    @Override
    public Product save(Product product, MultipartFile multipartFile,String[] sizes) {
        if(multipartFile!=null && product!=null){
            try {
                if(sizes!=null && sizes.length>0){
                    // TODO: 01.03.2021 add array of size
//                    product.setSizes(Arrays.toString(sizes));
                }
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
    public Product update(Product product, MultipartFile multipartFile, String[] sizes) {
        if(product!=null){
            Product productDB = productJPA.getOne(product.getId());

            productDB.setName(product.getName());
            productDB.setArticle(product.getArticle());
            productDB.setDescription(product.getDescription());
            productDB.setCategory(product.getCategory());
            productDB.setSeason(product.getSeason());
            productDB.setPrice(product.getPrice());
            productDB.setTypeOfProduct(product.getTypeOfProduct());

            if(sizes!=null && sizes.length>0){
                // TODO: 01.03.2021 add array of size
//                product.setSizes(Arrays.toString(sizes));
            }
            if(multipartFile!=null){
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
    public void deleteByID(int id) {
        productJPA.deleteById(id);
    }
}
