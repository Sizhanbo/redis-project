package com.itszb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisDemoApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testInt() {
        ValueOperations vpos = redisTemplate.opsForValue();
        System.out.println("vpos = " + vpos);
        HashOperations hops = redisTemplate.opsForHash();
        System.out.println("hops = " + hops);

        ListOperations lops = redisTemplate.opsForList();
        System.out.println("lops = " + lops);
        SetOperations sops = redisTemplate.opsForSet();
        System.out.println("sops = " + sops);
        ZSetOperations zops = redisTemplate.opsForZSet();
        System.out.println("zops = " + zops);
    }

    @Test
    void testString() {
        //获取操作String的对象
        ValueOperations ops = redisTemplate.opsForValue();
        //2.保存
        ops.set("city", "hangzhou");
        //3.获取
        String city = (String) ops.get("city");
        System.out.println("city = " + city);
        //4.保存的时候,设置一下存活时间setex
        ops.set("name", "张三", 30, TimeUnit.SECONDS);
        String name = (String) ops.get("name");
        System.out.println("name = " + name);
        //5.保存对象的时候,如果存在就不保存
        ops.setIfAbsent("gender", "女", 1, TimeUnit.MINUTES);
        String gender = (String) ops.get("gender");
        System.out.println("gender = " + gender);
    }

    @Test
    void testObject() {
        //获取操作String的对象
        ValueOperations ops = redisTemplate.opsForValue();
        //保存单个User对象
        User user = new User(1001, "张三", 18, "北京昌平沙河");
        ops.set("user:1001", user);

        //获取单个对象
        User user1001 = (User) ops.get("user:1001");
        System.out.println("user1001 = " + user1001);
        //保存多个User对象
        List<User> userList = new ArrayList<>();
        Collections.addAll(userList,
                new User(1001, "张三", 18, "北京昌平沙河"),
                new User(1002, "李四", 38, "江苏徐州沛县"),
                new User(1003, "王五", 28, "浙江杭州余杭"));
        ops.set("user:list", userList);

        //获取多个对象
        List<User> newList = (List<User>) ops.get("user:list");
        System.out.println("newList = " + newList);

    }

    @Test
    void testhash() {
        HashOperations ops = redisTemplate.opsForHash();
        //ops.put("user:info:1001", "id", "1001");
        //ops.put("user:info:1001", "name", "张三");
        //ops.put("user:info:1001", "age", "18");
        //ops.put("user:info:1001", "address", "山东济南历程");
        //  获取键值对
        ops.keys("user:info:1001").forEach(System.out::println);
        ops.values("user:info:1001").forEach(System.out::println);
        ops.entries("user:info:1001").forEach((key, value)-> System.out.println(key + ":" + value));
    }
}

