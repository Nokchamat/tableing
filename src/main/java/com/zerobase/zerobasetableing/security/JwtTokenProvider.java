package com.zerobase.zerobasetableing.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.key}")
    private String secretKey= "secretKey";

    private final Long tokenValidMillisecond = 1000L * 60 * 60 * 4;

    @PostConstruct
    protected void init() {
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));

        log.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId); // 내용
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 헤더
                .compact();

        return token;
    }

    public String getUserId(String token) {
        String userId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return userId;
    }

    public String resolveToken(HttpServletRequest request) {
        log.info("[resolveToken HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효 체크 시작");

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }

    }


}
