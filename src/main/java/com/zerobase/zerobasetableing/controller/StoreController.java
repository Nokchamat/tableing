package com.zerobase.zerobasetableing.controller;

import com.zerobase.zerobasetableing.domain.dto.StoreDto;
import com.zerobase.zerobasetableing.domain.model.Store;
import com.zerobase.zerobasetableing.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    StoreService storeService;

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

}
