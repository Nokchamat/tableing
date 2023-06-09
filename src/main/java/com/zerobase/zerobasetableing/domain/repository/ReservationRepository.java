package com.zerobase.zerobasetableing.domain.repository;

import com.zerobase.zerobasetableing.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByCustomerId (Long customerId);

    List<Reservation> findAllByStoreIdAndIsReservationIsTrue (Long StoreId);
    List<Reservation> findAllByStoreIdAndIsReservationIsFalse (Long StoreId);

}
