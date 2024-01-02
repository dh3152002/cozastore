package com.example.cozastore.service;

import com.example.cozastore.entity.CategoryEntity;
import com.example.cozastore.entity.ProductEntity;
import com.example.cozastore.repository.ProductRepository;
import com.example.cozastore.service.imp.FileServiceImp;
import com.example.cozastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService implements ProductServiceImp {
    @Autowired
    private FileServiceImp fileServiceImp;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(String title, double price, MultipartFile file, int idCategory, String tag) {
        fileServiceImp.save(file);

        ProductEntity productEntity=new ProductEntity();
        productEntity.setPrice(price);
        productEntity.setTitle(title);
        productEntity.setTags(tag!=null?tag:"");
        productEntity.setImages(file.getOriginalFilename());

        CategoryEntity categoryEntity=new CategoryEntity();
        categoryEntity.setId(idCategory);
        productEntity.setCategory(categoryEntity);

        try {
            productRepository.save(productEntity);
        }catch (Exception e){
            throw new RuntimeException("Lỗi insert product: "+e.getMessage());
        }
    }
}
