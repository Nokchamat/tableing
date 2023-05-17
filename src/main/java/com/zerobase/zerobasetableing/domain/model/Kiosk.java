package com.zerobase.zerobasetableing.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Kiosk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Reservation> reservationList;

}
