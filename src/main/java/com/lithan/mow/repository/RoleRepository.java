package com.lithan.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.ERole;
import com.lithan.mow.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
   Role findByName(ERole eRole);
}
