package com.zerobase.zerobasetableing.service.store;

import com.zerobase.zerobasetableing.domain.form.ReservationForm;
import com.zerobase.zerobasetableing.domain.form.VisitedForm;
import com.zerobase.zerobasetableing.domain.model.Customer;
import com.zerobase.zerobasetableing.domain.model.Reservation;
import com.zerobase.zerobasetableing.domain.model.Seller;
import com.zerobase.zerobasetableing.domain.model.Store;
import com.zerobase.zerobasetableing.domain.repository.CustomerRepository;
import com.zerobase.zerobasetableing.domain.repository.ReservationRepository;
import com.zerobase.zerobasetableing.domain.repository.SellerRepository;
import com.zerobase.zerobasetableing.domain.repository.StoreRepository;
import com.zerobase.zerobasetableing.exception.CustomException;
import com.zerobase.zerobasetableing.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final CustomerRepository customerRepository;

    private final StoreRepository storeRepository;

    private final SellerRepository sellerRepository;

    // 예약 요청하는 함수
    @Transactional
    public void requestReservation(ReservationForm form) {

        // 에약 요청한 시간이 현재로부터 15분 뒤 이상이 아니면 예약 요청이 안 됨
        if (!form.getRequestReservationTime().isAfter(LocalDateTime.now().plusMinutes(15))) {
            throw new CustomException(ErrorCode.PLEASE_RESERVATION_AFTER_15MINUTE);
        }

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
                .isVisited(false)
                .storeKioskNumber(store.getId() + store.getName())
                .build();

        reservationRepository.save(reservation);
    }

    // 고객 예약 리스트 가져오기
    public List<Reservation> getCustomerReservationList(Long customerId) {
        return reservationRepository.findAllByCustomerId(customerId);
    }

    // 셀러 예약 리스트 가져오기 (커스터머가 예약을 요청한 후 셀러가 예약을 받은 리스트)
    @Transactional
    public List<Reservation> getSellerReservationList(Long sellerId, Long storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        if (sellerId != store.getSeller().getId()) {
            throw new CustomException(ErrorCode.STORE_SELLER_NOT_MATCHED);
        }

        return reservationRepository.findAllByStoreIdAndIsReservationIsTrue(storeId);
    }

    // 커스터머로부터 요청된 예약 리스트 가져오기 (커스터머가 예약을 요청했지만 셀러가 아직 예약을 받지 않은 리스트)
    @Transactional
    public List<Reservation> getSellerReservationRequestList(Long sellerId, Long storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        if (sellerId != store.getSeller().getId()) {
            throw new CustomException(ErrorCode.STORE_SELLER_NOT_MATCHED);
        }

        return reservationRepository.findAllByStoreIdAndIsReservationIsFalse(storeId);
    }

    // 커스터머로부터 요청된 예약을 수락함
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

    // 에약이 완료된 이후 상점에 방문했을 때 방문했다고 체크함
    @Transactional
    public void customerVisited(VisitedForm form) {

        Reservation reservation =
                reservationRepository.findById(form.getReservationId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        // 예약 상태가 isReservation = true 인지 확인
        if (!reservation.isReservation()) {
            throw new CustomException(ErrorCode.NOT_FOUND_RESERVATION);
        }

//         10분 전인지 확인
        if (!LocalDateTime.now().isBefore(reservation.getCurrentReservationTime().minusMinutes(10)) ) {
            throw new CustomException(ErrorCode.LATE_TIME_VISITED);
        }

        // 도착 상태가 false 인지 확인하여 중복 알람 방지
        if (reservation.isVisited()) {
            throw new CustomException(ErrorCode.ALREADY_VISITED);
        }

        reservation.setVisited(true);
    }


}
