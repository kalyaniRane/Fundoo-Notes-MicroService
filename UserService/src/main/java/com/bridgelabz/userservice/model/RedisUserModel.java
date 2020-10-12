package com.bridgelabz.userservice.model;


import com.bridgelabz.userservice.dto.RedisUserDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RedisUserModel implements Serializable {
    private Integer userID;
    private String token;

    public RedisUserModel(RedisUserDto redisUserDto) {
        this.userID=redisUserDto.userID;
        this.token= redisUserDto.token;
    }

}
