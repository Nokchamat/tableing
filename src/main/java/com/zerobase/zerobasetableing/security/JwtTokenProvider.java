package com.zerobase.zerobasetableing.security;

import com.zerobase.zerobasetableing.exception.CustomException;
import com.zerobase.zerobasetableing.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //토큰 생성
    public String createToken(Long id, String userId) {
        Claims claims = Jwts.claims().setSubject(userId); // 내용
        claims.put("id", id);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 헤더
                .compact();

        return token;
    }

    //토큰 생성 시 넣었던 id 값 추출
    public Long getId(String token) {
        return Long.valueOf(
                String.valueOf(
                        Jwts.parser().setSigningKey(secretKey)
                                .parseClaimsJws(token).getBody().get("id")));
    }

    //토큰이 유효한지 체크
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }

    }


}
