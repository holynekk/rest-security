package com.course.apisecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

@Configuration
public class RedisConfig {

	@Bean
	public StatefulRedisConnection<String, String> redisConnection() {
		RedisClient redisClient = RedisClient.create("redis://localhost:6379");
		return redisClient.connect();
	}

}
