package com.lithan.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
