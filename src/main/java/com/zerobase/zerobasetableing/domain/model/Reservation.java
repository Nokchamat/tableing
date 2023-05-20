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

    // 연락이 필요한 상황이 있을 수 있기 때문에 휴대폰 번호
    private String phoneNumber;

    // 내역을 줄 때 가게의 이름이 있어야 보기 편함
    private String storeName;

    // 예약한 시간
    private LocalDateTime currentReservationTime;

    // 커스터머가 요청만 한 상태면 false, 요청 후 셀러가 예약을 받으면 true
    private boolean isReservation;

    // 예약자
    private Long customerId;

    // 예약된 가게 이름
    private Long storeId;

    // 키오스크 번호를 통하여 해당 매장에 방문했는지 확인
    private String storeKioskNumber;

    // 기본 false, 예약 후 매장에 도착하여 도착 함수를 호출하면 true
    private boolean isVisited;

}
