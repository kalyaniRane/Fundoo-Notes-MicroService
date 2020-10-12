package com.bridgelabz.userservice.utils.implementation;

import com.bridgelabz.userservice.exceptions.JWTException;
import com.bridgelabz.userservice.model.User;
import com.bridgelabz.userservice.properties.FileProperties;
import com.bridgelabz.userservice.utils.IToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Token implements IToken {

    @Autowired
    FileProperties jwtProperties;

    @Override
    public String generateVerificationToken(User user) {

        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getFullName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + jwtProperties.getVerificationMs()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getJwtSecret())
                .compact();
    }

    @Override
    public int decodeJWT(String jwt) throws JWTException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getJwtSecret()).parseClaimsJws(jwt).getBody();

            return Integer.parseInt(claims.getId());
        } catch (ExpiredJwtException e) {
            throw new JWTException("session time out");
        }
    }

    @Override
    public String generateLoginToken(User user) {

        long currentTime = System.currentTimeMillis();

        return Jwts.builder()
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getFullName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + jwtProperties.getJwtExpirationMs()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getJwtSecret())
                .compact();
    }

}
