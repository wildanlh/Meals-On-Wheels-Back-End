package com.lithan.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
