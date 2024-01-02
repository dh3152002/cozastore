package com.example.cozastore.controller;

import com.example.cozastore.service.imp.ProductServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServiceImp productServiceImp;

    private Logger logger= LoggerFactory.getLogger(ProductController.class);

    @GetMapping("")
    public ResponseEntity<?> getAllProduct(){
        return new ResponseEntity<>("get all product", HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insert(
            @RequestParam MultipartFile file,
            @RequestParam String title,
            @RequestParam double price,
            @RequestParam int idCategory,
            @RequestParam(required = false) String tag
    ){
        productServiceImp.save(title,price,file,idCategory,tag);
        return new ResponseEntity<>("insert product", HttpStatus.OK);
    }
}
