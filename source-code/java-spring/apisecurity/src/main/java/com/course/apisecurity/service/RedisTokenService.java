package com.course.apisecurity.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.apisecurity.entity.RedisToken;
import com.course.apisecurity.util.HmacUtil;
import com.course.apisecurity.util.SecureStringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.lettuce.core.api.StatefulRedisConnection;

@Service
public class RedisTokenService {

	@Autowired
	private StatefulRedisConnection<String, String> redisConnection;

	@Autowired
	private ObjectMapper objectMapper;

	public String HMAC_SECRET = "theHmacSecretKey";

	public String store(RedisToken token)
			throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException {
		var tokenId = SecureStringUtil.randomString(30);
		var tokenJson = objectMapper.writeValueAsString(token);

		var hmac = HmacUtil.hmac(tokenId, HMAC_SECRET);

		redisConnection.sync().set(tokenId, tokenJson);
		redisConnection.sync().expire(tokenId, 15 * 60);

		return StringUtils.join(tokenId, ".", hmac);
	}

	public Optional<RedisToken> read(String bearerToken)
			throws NoSuchAlgorithmException, JsonMappingException, JsonProcessingException, InvalidKeyException {
		var tokens = StringUtils.split(bearerToken, '.');
		if (!HmacUtil.isHmacMatch(tokens[0], HMAC_SECRET, tokens[1])) {
			return Optional.empty();
		}

		var tokenJson = redisConnection.sync().get(tokens[0]);

		if (StringUtils.isBlank(tokenJson)) {
			return Optional.empty();
		}

		var token = objectMapper.readValue(tokenJson, RedisToken.class);

		return Optional.of(token);
	}

	public void delete(String tokenId) {
		redisConnection.sync().del(tokenId);
	}

}
