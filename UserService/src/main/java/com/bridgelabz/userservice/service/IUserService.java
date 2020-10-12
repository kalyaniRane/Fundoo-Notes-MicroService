package com.bridgelabz.userservice.service;


import com.bridgelabz.userservice.dto.LoginDTO;
import com.bridgelabz.userservice.dto.RegisterUserDto;
import com.bridgelabz.userservice.model.User;

import javax.mail.MessagingException;
import java.util.List;

public interface IUserService {

    String userRegistration(RegisterUserDto registrationDTO, String requestURL) throws MessagingException;

    String sendVerificationMail(String email, String requestURL) throws MessagingException;

    String verifyEmail(String token);

    String userLogin(LoginDTO loginDTO);

    String resetPasswordLink(String email, String urlToken) throws MessagingException;

    String resetPassword(String password, String urlToken);

    List<User> getAllUsers(String userField);

    Integer getUser(String token);
}
