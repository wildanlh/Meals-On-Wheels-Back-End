package com.lithan.mow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

  Optional<Partner> findByEmail(String email);
}
