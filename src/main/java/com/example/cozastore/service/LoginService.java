package com.example.cozastore.service;

import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.repository.UserRepository;
import com.example.cozastore.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginServiceImp {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkLogin(String email,String password){
        UserEntity user=userRepository.findByEmail(email);

        return user!=null && passwordEncoder.matches(password,user.getPassword());
    }
}
