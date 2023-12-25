package com.example.cozastore.security;

import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomAuthenProvider implements AuthenticationProvider {
    @Autowired
    private LoginServiceImp loginServiceImp;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email= (String) authentication.getPrincipal();
        String password= (String) authentication.getCredentials();
        UserEntity user=loginServiceImp.checkLogin(email,password);

        if(user!=null){
            // Tạo 1 list nhận vào danh sách quyền theo chuẩn của Security
            List<GrantedAuthority> listRoles=new ArrayList<>();
            // Tạo ra 1 quyền và gán tên quyền truy vấn được từ database đế add vào list role ở trên
            SimpleGrantedAuthority role=new SimpleGrantedAuthority(user.getRole().getName());
            listRoles.add(role);
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken("","",listRoles);
            return authenticationToken;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
