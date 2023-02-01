package com.lithan.mow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.Order;
import com.lithan.mow.model.constraint.EStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderdBy(Customer customer);

    List<Order> findByStatus(EStatus eStatus);
}
