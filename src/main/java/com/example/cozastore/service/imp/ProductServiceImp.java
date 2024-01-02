package com.example.cozastore.service.imp;

import org.springframework.web.multipart.MultipartFile;

public interface ProductServiceImp {
    void save(String title, double price, MultipartFile file, int idCategory, String tag);
}
