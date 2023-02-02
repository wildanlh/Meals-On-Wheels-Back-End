package com.lithan.mow.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.payload.response.OrderResponse;
import com.lithan.mow.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

  @Autowired
  OrderRepository orderRepository;

  public List<OrderResponse> getOrderWithStatus(EStatus status) {
    List<OrderResponse> orderList = new ArrayList<>();
    orderRepository.findByStatus(status).forEach(data -> orderList.add(new OrderResponse(data)));
    return orderList;
  }
}
