package com.zerobase.zerobasetableing.controller;


import com.zerobase.zerobasetableing.domain.constants.ErrorCode;
import com.zerobase.zerobasetableing.domain.form.SignUpForm;
import com.zerobase.zerobasetableing.domain.model.Reservation;
import com.zerobase.zerobasetableing.exception.CustomException;
import com.zerobase.zerobasetableing.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    //회원가입
    @PostMapping("/signup")
    ResponseEntity<String> signUp(SignUpForm form){
        sellerService.signUp(form);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    //로그인

    //예약 내역 확인

    //예약 요청 확인

    //예약 요청 수락

    //가게 등록

}
