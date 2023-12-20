package com.example.cozastore.security;

import com.example.cozastore.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenProvider implements AuthenticationProvider {
    @Autowired
    private LoginServiceImp loginServiceImp;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email= (String) authentication.getPrincipal();
        String password= (String) authentication.getCredentials();

        if(loginServiceImp.checkLogin(email,password)){
            return authentication;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
