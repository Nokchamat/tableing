package com.zerobase.zerobasetableing.service.user;

import com.zerobase.zerobasetableing.exception.ErrorCode;
import com.zerobase.zerobasetableing.domain.form.SignInForm;
import com.zerobase.zerobasetableing.domain.form.SignUpForm;
import com.zerobase.zerobasetableing.domain.model.Seller;
import com.zerobase.zerobasetableing.domain.repository.SellerRepository;
import com.zerobase.zerobasetableing.exception.CustomException;
import com.zerobase.zerobasetableing.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    // 셀러 회원가입
    @Transactional
    public void signUp(SignUpForm form) {

        sellerRepository.save(Seller.builder()
                        .userId(form.getUserId())
                        //비밀번호 보안처리하여 저장
                        .password(passwordEncoder.encode(form.getPassword()))
                        .name(form.getName())
                        .age(form.getAge())
                        .phoneNumber(form.getPhoneNumber())
                .build());
    }

    //셀러 로그인
    public String signIn(SignInForm form) {
        Seller seller = sellerRepository
                .findByUserId(form.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(form.getPassword()
                , seller.getPassword())) {
            throw new CustomException(ErrorCode.UNMATCHED_ID_OR_PASSWORD);
        }

        return jwtTokenProvider.createToken(seller.getId(), seller.getUserId());
    }
}
