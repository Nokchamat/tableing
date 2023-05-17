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

    private String name;

    private String category;

    private String location;

    private String description;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

}
