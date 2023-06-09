package com.zerobase.zerobasetableing.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnore
    private Seller seller;

    //number : storeId + name
    private String kioskNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private List<Review> reviewList;

}
