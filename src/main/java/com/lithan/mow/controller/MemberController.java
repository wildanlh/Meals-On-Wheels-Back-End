package com.lithan.mow.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lithan.mow.model.MealPackage;
import com.lithan.mow.model.Order;
import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.payload.response.OrderResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.MealPackageRepository;
import com.lithan.mow.repository.OrderRepository;
import com.lithan.mow.service.CustomerService;

@PreAuthorize("hasRole('ROLE_MEMBER')")
@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MemberController {

   @Autowired
   MealPackageRepository mealPackageRepository;

   @Autowired
   OrderRepository orderRepository;

   @Autowired
   CustomerRepository customerRepository;

   @Autowired
   CustomerService customersService;

   @GetMapping("/order")
   public List<OrderResponse> getUncomplateOrder() {
      List<OrderResponse> orderList = new ArrayList<>();
      orderRepository.findByStatusIsNotAndOrderdBy(EStatus.ORDER_COMPLETE, customersService.getCurrentUser())
            .forEach(order -> orderList.add(new OrderResponse(order)));
      return orderList;
   }

   @GetMapping("/order/all")
   public List<OrderResponse> getAllOrder() {
      List<OrderResponse> orderList = new ArrayList<>();
      orderRepository.findByStatusAndOrderdBy(EStatus.DELIVERY_COMPLETE, customersService.getCurrentUser())
            .forEach(order -> orderList.add(new OrderResponse(order)));
      return orderList;
   }

   @GetMapping("/order/{id}/create")
   public MessageResponse orderMeal(@PathVariable Long id) {
      MealPackage meal = mealPackageRepository.findById(id).get();

      Order orderRequest = new Order();
      orderRequest.setMealPackage(meal);
      orderRequest.setOrderdOn(new Date());
      orderRequest.setStatus(EStatus.PENDING);
      orderRequest.setOrderdBy(customersService.getCurrentUser());

      orderRepository.save(orderRequest);

      return new MessageResponse("You Have Successfully Requested an Order");
   }

   @GetMapping("/order/{id}/complete")
   public MessageResponse complateOrder(@PathVariable Long id) {
      Order order = orderRepository.findById(id).get();
      order.setStatus(EStatus.ORDER_COMPLETE);

      orderRepository.save(order);

      return new MessageResponse("Happy Eating, Hope You are Enjoying Our Meal");
   }

}
