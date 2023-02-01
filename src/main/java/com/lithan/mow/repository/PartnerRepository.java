package com.lithan.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    
}
