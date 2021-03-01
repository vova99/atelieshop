package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.Size;
import com.oksanapiekna.atelieshop.jpa.SizeJPA;
import com.oksanapiekna.atelieshop.service.SizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SizeServiceImpl implements SizeService {
    private SizeJPA sizeJPA;

    @Override
    public Size save(Size size) {
        return sizeJPA.save(size);
    }

    @Override
    public Size update(Size size) {
        Size sizeDB = sizeJPA.getOne(size.getId());
        sizeDB.setSize(size.getSize());
        return save(sizeDB);
    }

    @Override
    public Size create(String sizeName, String categoryName) {
        if(!sizeName.isEmpty() && !categoryName.isEmpty()){
            Size size = new Size();
            size.setSize(sizeName);
            switch (categoryName){
                case "CLOTHES":
                    size.setCategory(Category.CLOTHES);
                    break;
                case "SHOES":
                    size.setCategory(Category.SHOES);
                    break;
                default:
                    return null;
            }
            return sizeJPA.save(size);
        }
        return null;
    }

    @Override
    public Size findById(int id) {
        return sizeJPA.findById(id).orElse(null);
    }

    @Override
    public List<Size> findAll() {
        return sizeJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        sizeJPA.deleteById(id);
    }
}
