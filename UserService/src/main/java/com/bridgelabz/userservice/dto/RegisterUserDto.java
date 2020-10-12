package com.bridgelabz.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z. ]+[ ]*[A-Za-z.]*$", message = "Please Enter Valid Full Name")
    public String fullName;

    @Column(nullable = false)
    @Pattern(regexp = "^([a-zA-Z0-9]{3,}([.|_|+|-]?[a-zA-Z0-9]+)?[@][a-zA-Z0-9]+[.][a-zA-Z]{2,3}([.]?[a-zA-Z]{2,3})?)$", message = "please Enter Valid EmailID")
    public String email;

    @Column(nullable = false)
    @Pattern(regexp = "^((?=[^@|#|&|%|$]*[@|&|#|%|$][^@|#|&|%|$]*$)*(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9#@$?]{8,})$", message = "Password Should contain One Uppercase and Symbol and greater than 6 character")
    public String password;

}
