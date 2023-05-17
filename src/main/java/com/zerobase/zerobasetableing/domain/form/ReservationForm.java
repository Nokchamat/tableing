package com.zerobase.zerobasetableing.domain.form;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationForm {

    private Long customerId;

    private Long storeId;

    private LocalDateTime requestReservationTime;

}
