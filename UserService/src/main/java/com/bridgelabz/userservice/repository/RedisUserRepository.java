package com.bridgelabz.userservice.repository;


import com.bridgelabz.userservice.model.RedisUserModel;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Repository
public class RedisUserRepository {

    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;
    private Jedis jedis=new Jedis();
    private String token="jwtToken";

    public RedisUserRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void save(RedisUserModel user) {
       // System.out.println(user.getUserID().toString());
        //hashOperations.put("USER", user.getUserID(), user.getToken());
        jedis.hset(token, String.valueOf(user.getUserID()),user.getToken());

    }

    public Map<String, String> findByToken(String userToken) {
//        Object user = hashOperations.get("USER", userToken);
        Map<String, String> user = jedis.hgetAll(token);
        return user;
    }

}
