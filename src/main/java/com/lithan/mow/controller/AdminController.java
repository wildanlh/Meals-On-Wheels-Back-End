package com.lithan.mow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.Order;
import com.lithan.mow.model.Partner;
import com.lithan.mow.model.constraint.ERole;
import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.payload.request.FeedbackRequest;
import com.lithan.mow.payload.request.MealPackageRequest;
import com.lithan.mow.payload.response.CustomerResponse;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.payload.response.OrderResponse;
import com.lithan.mow.payload.response.PartnerResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.FeedbackRepository;
import com.lithan.mow.repository.MealPackageRepository;
import com.lithan.mow.repository.OrderRepository;
import com.lithan.mow.repository.PartnerRepository;
import com.lithan.mow.service.OrderService;

@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CAREGIVER')")
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

  @Autowired
  PartnerRepository partnerRepository;

  @Autowired
  FeedbackRepository feedbackRepository;

  @GetMapping("/order/all")
  public List<OrderResponse> getOrder() {
    List<OrderResponse> orderList = new ArrayList<>();
    orderRepository.findAll().forEach(data -> orderList.add(new OrderResponse(data)));

    return orderList;
  }

  @GetMapping("/order")
  public List<OrderResponse> getPendingOrder() {
    List<OrderResponse> orderList = new ArrayList<>();
    orderList.addAll(orderService.getOrderWithStatus(EStatus.PENDING));
    orderList.addAll(orderService.getOrderWithStatus(EStatus.READY_TO_DELIVER));
    return orderList;
  }

  @GetMapping("/order/{orderId}/prepare/{partnerId}")
  public MessageResponse assignPartner(@PathVariable("orderId") Long orderId,
      @PathVariable("partnerId") Long partnerId) {
    Order order = orderRepository.findById(orderId).get();
    order.setPreparedBy(partnerRepository.findById(partnerId).get());

    orderRepository.save(order);
    return new MessageResponse(String.format("order %d assign to partner %d", orderId, partnerId));
  }

  @GetMapping("/order/prepared")
  public List<OrderResponse> getPreparedOrder() {
    return orderService.getOrderWithStatus(EStatus.PREPARING);
  }

  @GetMapping("/order/ready-to-deliver")
  public List<OrderResponse> getRedyToDeliverOrder() {
    return orderService.getOrderWithStatus(EStatus.READY_TO_DELIVER);
  }

  @GetMapping("/order/{orderId}/deliver/{riderId}")
  public MessageResponse assignRider(@PathVariable("orderId") Long orderId, @PathVariable("riderId") Long riderId) {
    Order order = orderRepository.findById(orderId).get();
    order.setDeliveredBy(customerRepository.findById(riderId).get());

    orderRepository.save(order);
    return new MessageResponse(String.format("order %d assign to rider %d", orderId, riderId));
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

  @GetMapping("/user")
  public List<CustomerResponse> getUser() {
    List<CustomerResponse> customerList = new ArrayList<>();
    customerRepository.findAll().forEach(data -> customerList.add(new CustomerResponse(data)));

    return customerList;
  }

  @GetMapping("/rider")
  public List<CustomerResponse> getRider() {
    List<CustomerResponse> customerList = new ArrayList<>();
    customerRepository.findByRole(ERole.ROLE_RIDER).forEach(data -> customerList.add(new CustomerResponse(data)));

    return customerList;
  }

  @GetMapping("/partner")
  public List<PartnerResponse> getPartner() {
    List<PartnerResponse> partnerList = new ArrayList<>();
    partnerRepository.findAll().forEach(data -> partnerList.add(new PartnerResponse(data)));

    return partnerList;
  }

  @GetMapping("/menu/all")
  public List<MealPackageRequest> getMeal() {
    List<MealPackageRequest> mealList = new ArrayList<>();
    mealPackRepository.findAll().forEach(data -> mealList.add(new MealPackageRequest(data)));

    return mealList;
  }

  @GetMapping("/user/inactive")
  public List<CustomerResponse> getInactiveUser() {
    List<CustomerResponse> customerList = new ArrayList<>();
    customerRepository.findByActive(false).forEach(data -> customerList.add(new CustomerResponse(data)));

    return customerList;
  }

  @GetMapping("/user/{id}/activate")
  public MessageResponse activateUser(@PathVariable Long id) {
    Customer user = customerRepository.findById(id).get();
    user.setActive(true);
    customerRepository.save(user);

    return new MessageResponse(String.format("activate user with id: %d", id));
  }

  @GetMapping("/partner/inactive")
  public List<PartnerResponse> getInactivePartner() {
    List<PartnerResponse> customerList = new ArrayList<>();
    partnerRepository.findByActive(false).forEach(data -> customerList.add(new PartnerResponse(data)));

    return customerList;
  }

  @GetMapping("/partner/{id}/activate")
  public MessageResponse activatePartner(@PathVariable Long id) {
    Partner partner = partnerRepository.findById(id).get();
    partner.setActive(true);
    partnerRepository.save(partner);

    return new MessageResponse(String.format("activate user with id: %d", id));
  }

  @GetMapping("/feedback")
  public List<FeedbackRequest> getFeedback() {
    List<FeedbackRequest> feedbackList = new ArrayList<>();
    feedbackRepository.findAll().forEach(data -> feedbackList.add(new FeedbackRequest(data)));

    return feedbackList;
  }

}
