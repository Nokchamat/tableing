package com.zerobase.zerobasetableing.service.customer;

import com.zerobase.zerobasetableing.domain.form.SignUpForm;
import com.zerobase.zerobasetableing.domain.model.Customer;
import com.zerobase.zerobasetableing.domain.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void signUp() {
        //given
        SignUpForm form = SignUpForm.builder()
                .userId("rkdtjdgur")
                .password("1234")
                .name("강성혁")
                .age(25)
                .phoneNumber("010-1234-5678")
                .build();

        //when
        customerService.signUp(form);

        Customer customer = customerRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("유저 없음"));


        //then
        assertEquals(customer.getUserId(), "rkdtjdgur");
        assertNotEquals(customer.getPassword(), "1234");
        assertTrue(passwordEncoder.matches("1234", customer.getPassword()));
        assertEquals(customer.getName(), "강성혁");
        assertEquals(customer.getPhoneNumber(), "010-1234-5678");

    }



}