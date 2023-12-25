package com.example.cozastore.service.imp;

import com.example.cozastore.entity.UserEntity;

public interface LoginServiceImp {
    UserEntity checkLogin(String email, String password);
}
