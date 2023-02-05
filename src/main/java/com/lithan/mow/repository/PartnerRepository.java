package com.lithan.mow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

  Optional<Partner> findByEmail(String email);

  List<Partner> findByActive(boolean active);
  
}
