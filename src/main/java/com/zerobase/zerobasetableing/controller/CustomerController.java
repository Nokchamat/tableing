package com.zerobase.zerobasetableing.controller;

import com.zerobase.zerobasetableing.domain.constants.ErrorCode;
import com.zerobase.zerobasetableing.domain.form.ReservationForm;
import com.zerobase.zerobasetableing.domain.form.SignInForm;
import com.zerobase.zerobasetableing.domain.form.SignUpForm;
import com.zerobase.zerobasetableing.domain.model.Reservation;
import com.zerobase.zerobasetableing.exception.CustomException;
import com.zerobase.zerobasetableing.security.JwtTokenProvider;
import com.zerobase.zerobasetableing.service.user.CustomerService;
import com.zerobase.zerobasetableing.service.store.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ReservationService reservationService;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @PostMapping("/signup")
    ResponseEntity<String> signUp(@RequestBody SignUpForm form){
        customerService.signUp(form);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    //로그인
    @PostMapping("/signin")
    ResponseEntity<String> signIn(@RequestBody SignInForm form) {
        String token = customerService.signIn(form);

        return ResponseEntity.ok(token);
    }


    //예약 - 토큰 인증 넣기
    @PostMapping("/reservation")
    ResponseEntity<String> requestReservation(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody ReservationForm form) {

        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }

        Reservation reservation = reservationService.requestReservation(form);

        return ResponseEntity.ok().body(reservation.toString() +
                "\n 예약 요청에 성공했습니다.");
    }

//    예약 내역 확인 - 토큰 인증 넣기
    @PostMapping("/reservation/list")
    ResponseEntity<List<Reservation>> getReservationList(
            @RequestHeader(name = "X-AUTH-TOKEN") String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }

        return ResponseEntity.ok(reservationService
                .getReservationList(
                        jwtTokenProvider.getId(token)
                ));
    }

    //예약 내역 수정 - 토큰 인증 넣기


    //수정 시에 사장이 봐서 수락을 눌러야 함


    //예약 내역 삭제 - 토큰 인증 넣기

}
