package com.zerobase.zerobasetableing.service.store;

import com.zerobase.zerobasetableing.domain.constants.ErrorCode;
import com.zerobase.zerobasetableing.domain.dto.StoreDto;
import com.zerobase.zerobasetableing.domain.model.Store;
import com.zerobase.zerobasetableing.domain.repository.StoreRepository;
import com.zerobase.zerobasetableing.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;


    //가게 정보 보기 // 리밋 걸어야될듯?
    public List<StoreDto> getStore(){
        return storeRepository.findAll()
                .stream().map(
                        store -> StoreDto.builder()
                                    .storeId(store.getId())
                                    .name(store.getName())
                                    .category(store.getCategory())
                                    .build())
                            .collect(Collectors.toList());
    }

    //가게 상세 정보 보기
    public Store getStoreDetail(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
    }






}
