package com.zerobase.zerobasetableing.service.customer;

import com.zerobase.zerobasetableing.domain.constants.ErrorCode;
import com.zerobase.zerobasetableing.domain.form.SignInForm;
import com.zerobase.zerobasetableing.domain.form.SignUpForm;
import com.zerobase.zerobasetableing.domain.model.Customer;
import com.zerobase.zerobasetableing.domain.repository.CustomerRepository;
import com.zerobase.zerobasetableing.exception.CustomException;
import com.zerobase.zerobasetableing.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(SignUpForm form) {
        customerRepository.save(Customer.builder()
                .userId(form.getUserId())
                .password(passwordEncoder.encode(form.getPassword()))
                .name(form.getName())
                .age(form.getAge())
                .phoneNumber(form.getPhoneNumber())
                .build());
    }


    public String signIn(SignInForm form) {
        Customer customer = customerRepository
                .findByUserId(form.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(form.getPassword()
                , customer.getPassword())) {
            throw new CustomException(ErrorCode.UNMATCHED_ID_OR_PASSWORD);
        }

        return jwtTokenProvider.createToken(customer.getUserId());
    }
}
