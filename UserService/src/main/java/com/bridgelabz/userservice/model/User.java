package com.bridgelabz.userservice.model;

import com.bridgelabz.userservice.dto.RegisterUserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Column(nullable = false)
    private boolean isVarified;

    @JsonIgnore
    private LocalDateTime accountCreatedDate;

    @JsonIgnore
    private LocalDateTime accountUpdatedDate;

    public User(RegisterUserDto registerUserDto) {
        this.fullName = registerUserDto.fullName;
        this.email = registerUserDto.email;
        this.password = registerUserDto.password;
        this.accountCreatedDate = LocalDateTime.now();
        this.accountUpdatedDate = LocalDateTime.now();
    }
}
