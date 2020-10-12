package com.bridgelabz.userservice.service;


import com.bridgelabz.userservice.dto.RedisUserDto;

public interface IRedisUser {

    String saveData(RedisUserDto redisUserDto);

    Integer getData(String token);

}
