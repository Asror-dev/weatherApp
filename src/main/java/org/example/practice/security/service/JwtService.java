package org.example.practice.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.ResponseEntity;

import javax.crypto.SecretKey;

public interface JwtService {
    String generateJwt(String id);
    SecretKey signWithKey();
    Jws<Claims> extractJwt(String jwt);

}
