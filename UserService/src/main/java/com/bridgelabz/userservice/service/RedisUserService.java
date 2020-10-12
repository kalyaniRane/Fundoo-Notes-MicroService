package com.bridgelabz.userservice.service;

import com.bridgelabz.userservice.dto.RedisUserDto;
import com.bridgelabz.userservice.model.RedisUserModel;
import com.bridgelabz.userservice.repository.RedisUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RedisUserService implements IRedisUser {

    @Autowired
    private RedisUserRepository userRepository;

    @Override
    public String saveData(RedisUserDto redisUserDto) {
        RedisUserModel redisUserModel = new RedisUserModel(redisUserDto);
        userRepository.save(redisUserModel);
        return "ADDED SUCCESSFULLY";
    }

    @Override
    public Integer getData(String token) {
        Map<String, String> userToken = userRepository.findByToken(token);
        System.out.println(userToken);
        String user = userToken.entrySet().stream().filter(stringStringEntry -> token.equals(stringStringEntry.getValue()))
                .map(Map.Entry::getKey).findFirst().get();
        Integer userID= Integer.parseInt(user);
        return userID;
    }


}
