package com.example.cozastore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("")
    public ResponseEntity<?> login(){
        return new ResponseEntity<>("login", HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String email,@RequestParam String password){
        UsernamePasswordAuthenticationToken user=new UsernamePasswordAuthenticationToken(email,password);
        authenticationManager.authenticate(user);
        return new ResponseEntity<>("signin",HttpStatus.OK);
    }
}
