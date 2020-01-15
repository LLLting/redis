package com.iyuedu.redis.controller;

import com.iyuedu.redis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class RedisController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public Object category(){

        //多线程测试一下缓存穿透问题
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        for (int i = 0; i <10000 ; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    categoryService.getAllCategory();
                }
            });
        }
        return categoryService.getAllCategory();
    }
}
