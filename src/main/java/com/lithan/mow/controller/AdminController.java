package com.lithan.mow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lithan.mow.payload.request.MealPackageRequest;
import com.lithan.mow.payload.response.CustomerResponse;
import com.lithan.mow.payload.response.OrderResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.MealPackageRepository;
import com.lithan.mow.repository.OrderRepository;

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

  @GetMapping("/order")
  public List<OrderResponse> getOrder() {
    List<OrderResponse> orderList = new ArrayList<>();
    orderRepository.findAll().forEach(data -> orderList.add(new OrderResponse(data)));

    return orderList;
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
