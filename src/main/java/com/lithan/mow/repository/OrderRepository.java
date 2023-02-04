package com.lithan.mow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.Partner;
import com.lithan.mow.model.Order;
import com.lithan.mow.model.constraint.EStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(EStatus eStatus);

    List<Order> findByOrderdBy(Customer member);

    List<Order> findByPreparedBy(Partner partner);

    List<Order> findByDeliveredBy(Customer rider);

    List<Order> findByStatusIsNotAndOrderdBy(EStatus status, Customer member);

    List<Order> findByStatusAndOrderdBy(EStatus status, Customer member);

    List<Order> findByStatusAndPreparedBy(EStatus status, Partner partner);

    List<Order> findByStatusAndDeliveredBy(EStatus status, Customer rider);
}
