package com.zerobase.zerobasetableing.controller.customer;

import com.zerobase.zerobasetableing.domain.dto.StoreDto;
import com.zerobase.zerobasetableing.domain.form.SignInForm;
import com.zerobase.zerobasetableing.domain.form.SignUpForm;
import com.zerobase.zerobasetableing.domain.model.Store;
import com.zerobase.zerobasetableing.domain.repository.StoreRepository;
import com.zerobase.zerobasetableing.service.customer.CustomerService;
import com.zerobase.zerobasetableing.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final StoreService storeService;

    //회원가입
    @PostMapping("/signup")
    ResponseEntity<String> signUp(SignUpForm form){
        customerService.signUp(form);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    //로그인
    @PostMapping("/signin")
    ResponseEntity<String> signIn(SignInForm form) {
        String token = customerService.signIn(form);

        return ResponseEntity.ok(token);
    }

    //가게 보기
    @GetMapping("/store")
    ResponseEntity<List<StoreDto>> getStore() {
        return ResponseEntity.ok(storeService.getStore());
    }


    //가게 상세보기
    @GetMapping("/store/detail")
    ResponseEntity<Store> getStoreDetail(@RequestParam Long id) {
        return ResponseEntity.ok(storeService.getStoreDetail(id));
    }


    //예약 - 토큰 인증 넣기


    //예약 내역 확인 - 토큰 인증 넣기


    //예약 내역 삭제 - 토큰 인증 넣기

}
