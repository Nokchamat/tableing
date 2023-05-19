package com.zerobase.zerobasetableing.service.store;

import com.zerobase.zerobasetableing.exception.ErrorCode;
import com.zerobase.zerobasetableing.domain.form.ReservationForm;
import com.zerobase.zerobasetableing.domain.model.Customer;
import com.zerobase.zerobasetableing.domain.model.Reservation;
import com.zerobase.zerobasetableing.domain.model.Seller;
import com.zerobase.zerobasetableing.domain.model.Store;
import com.zerobase.zerobasetableing.domain.repository.CustomerRepository;
import com.zerobase.zerobasetableing.domain.repository.ReservationRepository;
import com.zerobase.zerobasetableing.domain.repository.SellerRepository;
import com.zerobase.zerobasetableing.domain.repository.StoreRepository;
import com.zerobase.zerobasetableing.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final CustomerRepository customerRepository;

    private final StoreRepository storeRepository;

    private final SellerRepository sellerRepository;

    @Transactional
    public void requestReservation(ReservationForm form) {
        Store store = storeRepository.findById(form.getStoreId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        Customer customer = customerRepository.findById(form.getCustomerId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Reservation reservation = Reservation.builder()
                .customerId(form.getCustomerId())
                .storeId(form.getStoreId())
                .phoneNumber(customer.getPhoneNumber())
                .storeName(store.getName())
                .currentReservationTime(form.getRequestReservationTime())
                .isReservation(false)
                .build();


        reservationRepository.save(reservation);
    }

    public List<Reservation> getCustomerReservationList(Long customerId) {
        return reservationRepository.findAllByCustomerId(customerId);

    }

    @Transactional
    public List<Reservation> getSellerReservationList(Long sellerId, Long storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        if (sellerId != store.getSeller().getId()) {
            throw new CustomException(ErrorCode.STORE_SELLER_NOT_MATCHED);
        }

        return reservationRepository.findAllByStoreIdAndIsReservationIsTrue(storeId);
    }

    @Transactional
    public List<Reservation> getSellerReservationRequestList(Long sellerId, Long storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        if (sellerId != store.getSeller().getId()) {
            throw new CustomException(ErrorCode.STORE_SELLER_NOT_MATCHED);
        }

        return reservationRepository.findAllByStoreIdAndIsReservationIsFalse(storeId);
    }

    @Transactional
    public void acceptReservationRequest(Long sellerId, Long storeId, Long reservationId) {

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow( () -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        if ( seller.getStore().stream().noneMatch(store -> store.getId().equals(storeId)) ) {
            throw new CustomException(ErrorCode.STORE_SELLER_NOT_MATCHED);
        }

        if (!storeId.equals(reservation.getStoreId())) {
            throw new CustomException(ErrorCode.RESERVATION_STORE_NOT_MATCHED);
        }

        if (reservation.isReservation()) {
            throw new CustomException(ErrorCode.ALREADY_ACCEPT);
        }

        reservation.setReservation(true);

    }
}
