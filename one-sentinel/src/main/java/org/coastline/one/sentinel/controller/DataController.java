package org.coastline.one.sentinel.controller;

import org.coastline.one.sentinel.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 8/27/2019
 */
@RestController
public class DataController {

    @Autowired
    private RedisService redisService;

    static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(10));


    @GetMapping(value = "/hello")
    public String hello() throws Exception {
        for (int i = 0; i < 5; i++) {
            pool.submit(() -> {
                long start = System.currentTimeMillis();
                try {
                    String data = redisService.onlineRedis("Data");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.err.println(System.currentTimeMillis() - start);
            });
        }
        //redisService.onlineRedis2("Data2");

        return "data";
    }

    @GetMapping(value = "/world")
    public String world() throws Exception {
        String data = redisService.onlineRedis("Write");
        return data;
    }

    @GetMapping(value = "/pool")
    public String poolStatus() {
        String status = "当前排队线程数：" + pool.getQueue().size() + "\n"
                + "当前活动线程数：" + pool.getActiveCount() + "\n"
                + "执行完成线程数：" + pool.getCompletedTaskCount() + "\n"
                + "总线程数：" + pool.getTaskCount();
        return status;
    }
}
