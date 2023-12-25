package com.example.cozastore.filter;

import com.example.cozastore.jwt.JwtHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//OncePerRequestFilter: Kích hoạt khi người dùng gọi bất kì link nào
@Service
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson=new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bước 1: Lấy token từ giá trị của Header
        Optional<String> tokenOptional= Optional.ofNullable(request.getHeader("Authorization"));
        if(tokenOptional.isPresent()){
            // Bước 2: Cắt chuỗi bỏ chữ Bearer để lấy token
            //Cách 1:
            String token = tokenOptional.get().substring(7);
            //Cách 2:
//            tokenOptional.stream().map(data->data.substring(7));
            if(!token.isEmpty()){
                //Bước 3: Giải mã token
                String data=jwtHelper.decodeToken(token);
                // Tạo ra custom type để Gson hỗ trợ parse json kiểu list
                Type listType=new TypeToken<List<SimpleGrantedAuthority>>() {}.getType();
                List<GrantedAuthority> listRoles=gson.fromJson(data,listType);
                if(data!=null){
                    //Bước 4: Nếu giải mã thành công tạo ra Security Context Holder
                    // Tạo ContextHolder để bypass qua các filter của Security
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken("","",listRoles);
                    SecurityContext securityContext=SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}
