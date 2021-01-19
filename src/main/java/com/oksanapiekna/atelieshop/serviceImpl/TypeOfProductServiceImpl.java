package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.entity.TypeOfProduct;
import com.oksanapiekna.atelieshop.jpa.TypeOfProductJPA;
import com.oksanapiekna.atelieshop.service.TypeOfProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfProductServiceImpl implements TypeOfProductService {

    private TypeOfProductJPA typeOfProductJPA;

    public TypeOfProductServiceImpl(TypeOfProductJPA typeOfProductJPA) {
        this.typeOfProductJPA = typeOfProductJPA;
    }

    @Override
    public TypeOfProduct save(TypeOfProduct type) {
        return typeOfProductJPA.save(type);
    }

    @Override
    public TypeOfProduct update(TypeOfProduct type) {
        TypeOfProduct typeDB = typeOfProductJPA.getOne(type.getId());
        typeDB.setName(type.getName());
        return save(typeDB);
    }

    @Override
    public TypeOfProduct create(String nameType, String categoryName) {
        if(!nameType.isEmpty() && !categoryName.isEmpty()){
            TypeOfProduct typeOfProduct = new TypeOfProduct();
            typeOfProduct.setName(nameType);
            switch (categoryName){
                case "CLOTHES":
                    typeOfProduct.setCategory(Category.CLOTHES);
                    break;
                case "SHOES":
                    typeOfProduct.setCategory(Category.SHOES);
                    break;
                default:
                    return null;
            }
            return typeOfProductJPA.save(typeOfProduct);
        }
        return null;
    }

    @Override
    public TypeOfProduct findById(int id) {
        return typeOfProductJPA.getOne(id);
    }

    @Override
    public List<TypeOfProduct> findAll() {
        return typeOfProductJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        typeOfProductJPA.deleteById(id);
    }
}
