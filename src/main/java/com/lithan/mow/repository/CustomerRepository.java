package com.lithan.mow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.Role;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

   Optional<Customer> findByEmail(String email);

   Boolean existsByEmail(String email);

   // percobaan
   List<Customer> findByRoles(Role roles);
}
