package com.zerobase.zerobasetableing.service.seller;

import com.zerobase.zerobasetableing.domain.form.SignUpForm;
import com.zerobase.zerobasetableing.domain.model.Seller;
import com.zerobase.zerobasetableing.domain.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpForm form) {
        sellerRepository.save(Seller.builder()
                        .userId(form.getUserId())
                        .password(passwordEncoder.encode(form.getUserId()))
                        .name(form.getName())
                        .age(form.getAge())
                        .phoneNumber(form.getPhoneNumber())
                .build());
    }
}
