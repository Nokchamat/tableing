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
public class Reservation extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String storeName;

    private LocalDateTime currentReservationTime;

    private LocalDateTime requestModifyReservationTime;

    private boolean isReservation;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "kiosk_id")
    private Kiosk kiosk;

}