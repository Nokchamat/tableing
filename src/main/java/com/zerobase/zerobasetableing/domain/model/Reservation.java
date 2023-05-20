package com.zerobase.zerobasetableing.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String storeName;

    private LocalDateTime currentReservationTime;

    private boolean isReservation;

    private Long customerId;

    private Long storeId;

    private String storeKioskNumber;

    private boolean isVisited;

}
