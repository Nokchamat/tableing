package com.zerobase.zerobasetableing.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void tokenTest() {
        //given
        String token = jwtTokenProvider
                         .createToken(1L, "rkdtjdgur");

        //when
        Long id = jwtTokenProvider.getId(token);

        //then
        assertEquals(id, 1L);

    }

}