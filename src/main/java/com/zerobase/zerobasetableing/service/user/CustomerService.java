package com.zerobase.zerobasetableing.service.user;

import com.zerobase.zerobasetableing.domain.form.ReviewForm;
import com.zerobase.zerobasetableing.domain.model.Reservation;
import com.zerobase.zerobasetableing.domain.model.Review;
import com.zerobase.zerobasetableing.domain.model.Store;
import com.zerobase.zerobasetableing.domain.repository.ReservationRepository;
import com.zerobase.zerobasetableing.domain.repository.ReviewRepository;
import com.zerobase.zerobasetableing.domain.repository.StoreRepository;
import com.zerobase.zerobasetableing.exception.ErrorCode;
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

    private final ReservationRepository reservationRepository;

    private final StoreRepository storeRepository;

    private final PasswordEncoder passwordEncoder;

    private final ReviewRepository reviewRepository;

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

        return jwtTokenProvider.createToken(customer.getId(), customer.getUserId());
    }

    public void writeReview(ReviewForm form) {

        if (reviewRepository.findByReservationId(form.getReservationId()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_REVIEWED);
        }

        Reservation reservation =
                reservationRepository.findById(form.getReservationId())
                        .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        if (!reservation.isReservation() || !reservation.isVisited()) {
            throw new CustomException(ErrorCode.NOT_VISITED);
        }

        Store store = storeRepository.findById(reservation.getStoreId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        store.getReviewList().add(
                reviewRepository.save(
                        Review.builder()
                            .text(form.getText())
                            .star(form.getStar())
                            .reservationId(form.getReservationId())
                            .build()
                )
        );

        storeRepository.save(store);

    }
}
