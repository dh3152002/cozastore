package com.example.cozastore.controller;

import com.example.cozastore.jwt.JwtHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @GetMapping("")
    public ResponseEntity<?> login(){
        // Tạo key mã hóa cho token
//        SecretKey key= Jwts.SIG.HS256.key().build();
//        String strKey= Encoders.BASE64.encode(key.getEncoded());
//        System.out.println("Kiem tra: "+strKey);
        String data=jwtHelper.decodeToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxsbyBqd3QifQ.qHvgtMtzCnS3J1R4AETeBTqcf1W1nDU_zZBNYaCU9Pk");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String email,@RequestParam String password){
        UsernamePasswordAuthenticationToken user=new UsernamePasswordAuthenticationToken(email,password);
        authenticationManager.authenticate(user);

        String token=jwtHelper.generateToken("hello jwt");

        return new ResponseEntity<>(token,HttpStatus.OK);
    }
}
