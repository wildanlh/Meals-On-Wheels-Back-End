package com.lithan.mow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.payload.request.MealPackageRequest;
import com.lithan.mow.payload.response.CustomerResponse;
import com.lithan.mow.payload.response.OrderResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.MealPackageRepository;
import com.lithan.mow.repository.OrderRepository;
import com.lithan.mow.service.OrderService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  MealPackageRepository mealPackRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  OrderService orderService;

  @GetMapping("/order/all")
  public List<OrderResponse> getOrder() {
    List<OrderResponse> orderList = new ArrayList<>();
    orderRepository.findAll().forEach(data -> orderList.add(new OrderResponse(data)));

    return orderList;
  }

  @GetMapping("/order/pending")
  public List<OrderResponse> getPendingOrder() {
    return orderService.getOrderWithStatus(EStatus.PENDING);
  }

  @GetMapping("/order/prepared")
  public List<OrderResponse> getPreparedOrder() {
    return orderService.getOrderWithStatus(EStatus.PREPARING);
  }

  @GetMapping("/order/ready-to-deliver")
  public List<OrderResponse> getRedyToDeliverOrder() {
    return orderService.getOrderWithStatus(EStatus.READY_TO_DELIVER);
  }

  @GetMapping("/order/on-delivery")
  public List<OrderResponse> getOnDeliveryOrder() {
    return orderService.getOrderWithStatus(EStatus.ON_DELIVERY);
  }

  @GetMapping("/order/delivery-complate")
  public List<OrderResponse> getDeliveryComplateOrder() {
    return orderService.getOrderWithStatus(EStatus.DELIVERY_COMPLETE);
  }

  @GetMapping("/order/complate")
  public List<OrderResponse> getComplateOrder() {
    return orderService.getOrderWithStatus(EStatus.ORDER_COMPLETE);

  }

  @GetMapping("/meal")
  public List<MealPackageRequest> getMeal() {
    List<MealPackageRequest> mealList = new ArrayList<>();
    mealPackRepository.findAll().forEach(data -> mealList.add(new MealPackageRequest(data)));

    return mealList;
  }

  @GetMapping("/user")
  public List<CustomerResponse> getUser() {
    List<CustomerResponse> customerList = new ArrayList<>();
    customerRepository.findAll().forEach(data -> customerList.add(new CustomerResponse(data)));

    return customerList;
  }

}
