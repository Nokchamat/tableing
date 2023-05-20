package com.zerobase.zerobasetableing.service.store;

import com.zerobase.zerobasetableing.exception.ErrorCode;
import com.zerobase.zerobasetableing.domain.dto.StoreDto;
import com.zerobase.zerobasetableing.domain.form.RegisterStoreForm;
import com.zerobase.zerobasetableing.domain.model.Seller;
import com.zerobase.zerobasetableing.domain.model.Store;
import com.zerobase.zerobasetableing.domain.repository.SellerRepository;
import com.zerobase.zerobasetableing.domain.repository.StoreRepository;
import com.zerobase.zerobasetableing.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final SellerRepository sellerRepository;

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

    //가게 등록하기
    @Transactional
    public Store registerStore(RegisterStoreForm form, Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Store store = Store.builder()
                .name(form.getStoreName())
                .category(form.getCategory())
                .location(form.getLocation())
                .description(form.getDescription())
                .seller(seller)
                .reviewList(new ArrayList<>())
                .build();

        Store savedStore = storeRepository.save(store);
        savedStore.setKioskNumber(savedStore.getId() + savedStore.getName());

        seller.getStore().add(store);

        return store;
    }

}
