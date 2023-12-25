package com.example.cozastore.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${token.key}")
    private String strKey;

    private long expiredTime=8*60*60*1000;

    public String generateToken(String data){
//        Chuyển key đã lưu trữ từ dạng base64 về SecretKey
        SecretKey key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
//        long futureDate= Calendar.getInstance().getTimeInMillis();
        Date date=new Date();
        long futureMilis=date.getTime()+expiredTime;
        Date futureDate=new Date(futureMilis);
        return Jwts.builder().subject(data).expiration(futureDate).signWith(key).compact();
    }

    public String decodeToken(String token){
        SecretKey key=Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        String data=null;
        try {
            data=Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        }catch (Exception e){
            System.out.println("Lỗi parser token: "+e.getMessage());
        }
        return data;
    }
}
