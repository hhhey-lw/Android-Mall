package com.wei.diploma_project;

import com.wei.diploma_project.bean.CommentBean;
import com.wei.diploma_project.mapper.GoodMapper;
import com.wei.diploma_project.mapper.OrderMapper;
import com.wei.diploma_project.service.CommentService;
import com.wei.diploma_project.service.GoodService;
import com.wei.diploma_project.service.impl.RedisServiceImpl;
import com.wei.diploma_project.util.ItemBasedCF;
import com.wei.diploma_project.util.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// @RunWith(SpringRunner.class)
@SpringBootTest
class DiplomaProjectApplicationTests {

    @Autowired
    private RedisServiceImpl redisService;

    @Test
    void contextLoads() {
        String phoneNumber = "13197722537";
        String code = "";
        Integer duration = 5 * 60;

        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            code += random.nextInt(10);
        }

        // System.err.println(code);
        redisService.cacheValue(phoneNumber, code, duration);
        System.out.println(redisService.containsValueKey(phoneNumber));
        System.out.println(redisService.getValue(phoneNumber));
        // redisService.removeValue(phoneNumber);
        // try {
        //     MessageUtil.sendMessage();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

}
