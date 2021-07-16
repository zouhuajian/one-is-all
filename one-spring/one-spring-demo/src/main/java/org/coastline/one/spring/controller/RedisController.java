package org.coastline.one.spring.controller;

import org.coastline.one.spring.model.Result;
import org.coastline.one.spring.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    //@Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/redis/set")
    public Result<String> setKey(@RequestParam("key") String key, @RequestParam("value") String value) {
        return Result.ofSuccess(value);
    }

    @GetMapping(value = "/redis/get")
    public Result<String> getList(@RequestParam("key") String key) {
        return Result.ofSuccess(key);
    }

    //@Bean
    public JedisConnectionFactory buildJedisConnectionFactory() {
        RedisClusterConfiguration clusterConfig;
        JedisClientConfiguration clientConfig;
        return new JedisConnectionFactory();
    }
}