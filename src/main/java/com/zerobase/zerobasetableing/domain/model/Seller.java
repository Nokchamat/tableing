package com.zerobase.zerobasetableing.domain.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    private String password;

    private String name;

    private Integer age;

    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

}
