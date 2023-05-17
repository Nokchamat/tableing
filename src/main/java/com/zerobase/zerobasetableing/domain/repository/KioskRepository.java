package com.zerobase.zerobasetableing.domain.repository;

import com.zerobase.zerobasetableing.domain.model.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KioskRepository extends JpaRepository<Kiosk, Long> {
}
