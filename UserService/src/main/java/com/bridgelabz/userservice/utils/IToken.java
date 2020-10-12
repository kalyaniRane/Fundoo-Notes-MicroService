package com.bridgelabz.userservice.utils;


import com.bridgelabz.userservice.exceptions.JWTException;
import com.bridgelabz.userservice.model.User;

public interface IToken {

    int decodeJWT(String jwt) throws JWTException;
    String generateVerificationToken(User user);
    String generateLoginToken(User user);

}
