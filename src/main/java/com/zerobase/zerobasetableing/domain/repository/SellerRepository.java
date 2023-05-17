package com.zerobase.zerobasetableing.domain.repository;

import com.zerobase.zerobasetableing.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
}
