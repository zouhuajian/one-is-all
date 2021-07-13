package org.coastline.one.spring.controller;

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

    @RequestMapping(value = "/redis/set")
    public void setKey(@RequestParam("key") String key, @RequestParam("value") String value) {
        System.out.println("set success");
    }

    @GetMapping(value = "/redis/get")
    public String getList(@RequestParam("key") String key) {
        return key;
    }

    //@Bean
    public JedisConnectionFactory buildJedisConnectionFactory() {
        RedisClusterConfiguration clusterConfig;
        JedisClientConfiguration clientConfig;
        return new JedisConnectionFactory();
    }
}