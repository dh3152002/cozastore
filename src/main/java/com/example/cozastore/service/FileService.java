package com.example.cozastore.service;

import com.example.cozastore.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImp {
    @Value("${root.path.upload}")
    private String root;

    @Override
    public void save(MultipartFile file) {
        Path rootPath= Paths.get(root);
        try {
            if(!Files.exists(rootPath)){
                Files.createDirectories(rootPath);
            }
            Files.copy(file.getInputStream(),rootPath.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new RuntimeException("Lỗi upload file: "+e.getMessage());
        }
    }
}
