package com.zerobase.zerobasetableing.controller;

import com.zerobase.zerobasetableing.domain.constants.ErrorCode;
import com.zerobase.zerobasetableing.domain.dto.StoreDto;
import com.zerobase.zerobasetableing.domain.form.RegisterStoreForm;
import com.zerobase.zerobasetableing.domain.model.Store;
import com.zerobase.zerobasetableing.exception.CustomException;
import com.zerobase.zerobasetableing.security.JwtTokenProvider;
import com.zerobase.zerobasetableing.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;
    private final JwtTokenProvider jwtTokenProvider;

    //가게 보기
    @GetMapping
    ResponseEntity<List<StoreDto>> getStore() {
        return ResponseEntity.ok(storeService.getStore());
    }


    //가게 상세보기
    @GetMapping("/detail")
    ResponseEntity<Store> getStoreDetail(@RequestParam Long id) {
        return ResponseEntity.ok(storeService.getStoreDetail(id));
    }

    //가게 등록하기
    @PostMapping("/register")
    ResponseEntity<Store> registerStore(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody RegisterStoreForm form ) {

        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }

        return ResponseEntity.ok(
                storeService.registerStore(form, jwtTokenProvider.getId(token)));
    }

}
