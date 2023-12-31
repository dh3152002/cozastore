package com.example.cozastore.controller;

import com.example.cozastore.jwt.JwtHelper;
import com.example.cozastore.payload.response.BaseResponse;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson=new Gson();

    private Logger logger= LoggerFactory.getLogger(LoginController.class);

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
        Authentication authentication=authenticationManager.authenticate(user);
        authentication.getAuthorities();

        String json=gson.toJson(authentication.getAuthorities());

        String token=jwtHelper.generateToken(json);

        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setData(token);

        logger.info("Response: "+baseResponse);

        return new ResponseEntity<>(token,HttpStatus.OK);
    }
}
