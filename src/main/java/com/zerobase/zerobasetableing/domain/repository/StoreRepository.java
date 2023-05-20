package com.zerobase.zerobasetableing.domain.repository;

import com.zerobase.zerobasetableing.domain.model.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

//    @Override
//    @EntityGraph(attributePaths = {"reviewList"}, type = EntityGraph.EntityGraphType.LOAD)
//    Optional<Store> findById(Long id);
}
