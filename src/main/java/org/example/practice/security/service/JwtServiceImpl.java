package org.example.practice.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${weather.web.config.security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${weather.web.config.security.jwt.expiration}")
    private Long EXPIRATION;


    @Override
    public String generateJwt(String id) {
        return Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .issuedAt(new Date())
                .signWith(signWithKey())
                .subject(id)
                .compact();
    }



    @Override
    public SecretKey signWithKey() {
        ;
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    @Override
    public Jws<Claims> extractJwt(String jwt) {
        return Jwts.parser()
                .verifyWith(signWithKey())
                .build()
                .parseSignedClaims(jwt);
    }


}
