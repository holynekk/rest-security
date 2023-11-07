package com.holynekk.apisecurity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holynekk.apisecurity.entity.RedisToken;
import com.holynekk.apisecurity.util.SecureStringUtil;
import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisTokenService {

    @Autowired
    private StatefulRedisConnection<String, String> redisConnection;

    @Autowired
    private ObjectMapper objectMapper;

    public String store(RedisToken token) {
        String tokenId = SecureStringUtil.randomString(30);
        String tokenJson;
        try {
            tokenJson = objectMapper.writeValueAsString(token);
            redisConnection.sync().set(tokenId, tokenJson);
            redisConnection.sync().expire(tokenId, 15*60);
            return tokenId;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return StringUtils.EMPTY;
        }
    }

    public Optional<RedisToken> read(String tokenId) {
        String tokenJson = redisConnection.sync().get(tokenId);
        
        if (StringUtils.isBlank(tokenJson)) {
            return Optional.empty();
        }

        try {
            RedisToken token = objectMapper.readValue(tokenJson, RedisToken.class);
            return Optional.of(token);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void delete(String tokenId) {
        redisConnection.sync().del(tokenId);
    }
}
