package com.itszb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * SpringBoot 3.0.2 Redis 序列化配置类
 * 解决默认JDK序列化乱码问题，键用String序列化，值用JSON序列化（保留类型信息）
 */
@Configuration
public class RedisConfig {

    /**
     * 自定义RedisTemplate配置，统一序列化规则
     * @param redisConnectionFactory Redis连接工厂（SpringBoot自动配置）
     * @return 配置好的RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 1. 创建RedisTemplate对象，指定键为String类型，值为Object类型
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 2. 设置Redis连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 3. 配置键（Key）的序列化器：StringRedisSerializer（纯字符串，无乱码）
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);          // 普通键序列化
        redisTemplate.setHashKeySerializer(stringRedisSerializer);      // Hash类型的键序列化

        // 4. 配置值（Value）的序列化器：GenericJackson2JsonRedisSerializer（JSON格式，保留类型）
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(jsonRedisSerializer);          // 普通值序列化
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);      // Hash类型的值序列化

        // 5. 初始化RedisTemplate（必须调用，否则配置不生效）
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /**
     * 可选：配置StringRedisTemplate（专门处理String类型，SpringBoot默认已配置，此处可自定义增强）
     * StringRedisTemplate默认就是String序列化，如需特殊配置可重写
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        // StringRedisTemplate默认键值都是StringRedisSerializer，无需额外配置
        return stringRedisTemplate;
    }
}