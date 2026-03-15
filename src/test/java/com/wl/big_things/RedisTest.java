package com.wl.big_things;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //@Test
    public void testSet()
    {
        ValueOperations<String,String>operations=stringRedisTemplate.opsForValue();
        operations.set("username","zhangsan");
    }
    //@Test
    public void testGet()
    {
        ValueOperations<String,String>operations=stringRedisTemplate.opsForValue();
        System.out.println(operations.get("username"));
    }
}
