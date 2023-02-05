package com.lithan.mow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.constraint.ERole;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

   Optional<Customer> findByEmail(String email);

   Boolean existsByEmail(String email);

   List<Customer> findByRole(ERole roles);

   List<Customer> findByRoleAndActive(ERole roles, boolean active);
   
   List<Customer> findByRoleIsNot(ERole roles);

   List<Customer> findByActive(boolean active);
   

}
