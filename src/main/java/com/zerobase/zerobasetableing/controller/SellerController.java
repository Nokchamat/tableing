package com.zerobase.zerobasetableing.controller;


import com.zerobase.zerobasetableing.domain.constants.ErrorCode;
import com.zerobase.zerobasetableing.domain.form.SignInForm;
import com.zerobase.zerobasetableing.domain.form.SignUpForm;
import com.zerobase.zerobasetableing.domain.model.Reservation;
import com.zerobase.zerobasetableing.exception.CustomException;
import com.zerobase.zerobasetableing.security.JwtTokenProvider;
import com.zerobase.zerobasetableing.service.store.ReservationService;
import com.zerobase.zerobasetableing.service.user.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ReservationService reservationService;

    //회원가입
    @PostMapping("/signup")
    ResponseEntity<String> signUp(@RequestBody SignUpForm form){
        sellerService.signUp(form);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    //로그인
    @PostMapping("/signin")
    ResponseEntity<String> signIn(@RequestBody SignInForm form) {
        return ResponseEntity.ok( sellerService.signIn(form));
    }

    //예약 내역 확인
    @GetMapping("/reservation/list")
    ResponseEntity<List<Reservation>> getReservationList(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestParam(name = "id") String storeId) {

        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }

        List<Reservation> reservationList = reservationService.getSellerReservationList(
                jwtTokenProvider.getId(token), Long.valueOf(storeId));

        return ResponseEntity.ok(reservationList);
    }

    //예약 요청 확인
    @GetMapping("/reservation/request")
    ResponseEntity<List<Reservation>> getReservationRequestList(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestParam(name = "id") String storeId) {

        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }

        List<Reservation> reservationList = reservationService.getSellerReservationRequestList(
                jwtTokenProvider.getId(token), Long.valueOf(storeId));

        return ResponseEntity.ok(reservationList);
    }


    //예약 요청 수락


}
