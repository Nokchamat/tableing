package com.zerobase.zerobasetableing.service.store;

import com.zerobase.zerobasetableing.domain.constants.ErrorCode;
import com.zerobase.zerobasetableing.domain.form.ReservationForm;
import com.zerobase.zerobasetableing.domain.model.Customer;
import com.zerobase.zerobasetableing.domain.model.Reservation;
import com.zerobase.zerobasetableing.domain.model.Store;
import com.zerobase.zerobasetableing.domain.repository.CustomerRepository;
import com.zerobase.zerobasetableing.domain.repository.ReservationRepository;
import com.zerobase.zerobasetableing.domain.repository.StoreRepository;
import com.zerobase.zerobasetableing.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final CustomerRepository customerRepository;

    private final StoreRepository storeRepository;

    public Reservation requestReservation(ReservationForm form) {
        Store store = storeRepository.findById(form.getStoreId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        Customer customer = customerRepository.findById(form.getCustomerId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Reservation reservation = Reservation.builder()
                .customerId(customer.getId())
                .phoneNumber(customer.getPhoneNumber())
                .storeName(store.getName())
                .currentReservationTime(form.getRequestReservationTime())
                .isReservation(false)
                .build();

        customer.getReservationList().add(reservation);
        store.getKiosk().getReservationList().add(reservation);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationList(String userId) {
        return customerRepository.findByUserId(userId)
                .get().getReservationList();
    }

}
