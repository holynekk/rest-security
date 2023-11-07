package com.holynekk.apisecurity.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.holynekk.apisecurity.entity.JwtData;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    private static final String HMAC_SECRET = "theHmacSecretKeyForJwt";

    private static final String ISSUER = "apisecurity.com";

    private static final String[] AUDIENCE = { "https://apisecurity.com", "https://www.apisecurity.com" };

    private static final String SAMPLE_PRIVATE_CLAIM = "just-some-private-claim-to-be-validated";

    public String store(JwtData jwtData) {
        Algorithm algorithm = Algorithm.HMAC256(HMAC_SECRET);
        LocalDateTime expiresAt = LocalDateTime.now(ZoneOffset.UTC).plusMinutes(15);

        return JWT.create().withSubject(jwtData.getUsername()).withIssuer(ISSUER).withAudience(AUDIENCE)
                .withExpiresAt(Date.from(expiresAt.toInstant(ZoneOffset.UTC)))
                .withClaim("dummyAttribute", jwtData.getDummyAttribute())
                .withClaim("samplePrivateClaim", SAMPLE_PRIVATE_CLAIM).sign(algorithm);
    }

    public Optional<JwtData> read(String jwtToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(HMAC_SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(ISSUER).withAudience(AUDIENCE).acceptExpiresAt(60)
                    .withClaim("samplePrivateClaim", SAMPLE_PRIVATE_CLAIM).build();

            DecodedJWT decodedJwt = jwtVerifier.verify(jwtToken);

            JwtData jwtData = new JwtData();
            jwtData.setUsername(decodedJwt.getSubject());
            jwtData.setDummyAttribute(decodedJwt.getClaim("dummyAttribute").asString());

            return Optional.of(jwtData);
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }
}
