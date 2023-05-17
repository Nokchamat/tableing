package com.zerobase.zerobasetableing.domain.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String location;

    private String description;

    @OneToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kiosk_id", nullable = false)
    private Kiosk kiosk;


}
