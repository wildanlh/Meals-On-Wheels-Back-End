package com.lithan.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Donate;

public interface DonateRepository extends JpaRepository<Donate, Long> {
    
}
