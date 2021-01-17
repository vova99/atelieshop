package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.jpa.ProductJPA;
import com.oksanapiekna.atelieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductJPA productJPA;

    @Autowired
    public ProductServiceImpl(ProductJPA productJPA) {
        this.productJPA = productJPA;
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
