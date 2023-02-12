package com.lithan.mow.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.MealPackage;
import com.lithan.mow.model.Order;
import com.lithan.mow.model.Partner;
import com.lithan.mow.model.constraint.ERole;
import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.payload.request.FeedbackRequest;
import com.lithan.mow.payload.request.MealPackageRequest;
import com.lithan.mow.payload.response.CustomerResponse;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.payload.response.OrderResponse;
import com.lithan.mow.payload.response.UserCountResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.FeedbackRepository;
import com.lithan.mow.repository.MealPackageRepository;
import com.lithan.mow.repository.OrderRepository;
import com.lithan.mow.repository.PartnerRepository;
import com.lithan.mow.service.OrderService;

@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CAREGIVER','ROLE_PARTNER')")
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

  @GetMapping("/order/pending")
  public List<OrderResponse> getPendingOrder() {
    return orderService.getOrderWithStatus(EStatus.PENDING);
  }

  @GetMapping("/order/{orderId}/prepare/{partnerId}")
  public MessageResponse assignPartner(@PathVariable("orderId") Long orderId,
      @PathVariable("partnerId") Long partnerId) {
    Order order = orderRepository.findById(orderId).get();
    order.setPreparedBy(partnerRepository.findById(partnerId).get());

    orderRepository.save(order);
    return new MessageResponse(String.format("order %d assign to partner %d", orderId, partnerId));
  }

  @GetMapping("/order/ready-to-deliver")
  public List<OrderResponse> getReadyToDeliverOrder() {
    return orderService.getOrderWithStatus(EStatus.READY_TO_DELIVER);
  }

  // @GetMapping("/order/prepared")
  // public List<OrderResponse> getPreparedOrder() {
  // return orderService.getOrderWithStatus(EStatus.PREPARING);
  // }

  // @GetMapping("/order/ready-to-deliver")
  // public List<OrderResponse> getRedyToDeliverOrder() {
  // return orderService.getOrderWithStatus(EStatus.READY_TO_DELIVER);
  // }

  @GetMapping("/order/{orderId}/deliver/{riderId}")
  public MessageResponse assignRider(@PathVariable("orderId") Long orderId, @PathVariable("riderId") Long riderId) {
    Order order = orderRepository.findById(orderId).get();
    order.setDeliveredBy(customerRepository.findById(riderId).get());

    orderRepository.save(order);
    return new MessageResponse(String.format("order %d assign to rider %d", orderId, riderId));
  }

  // @GetMapping("/order/on-delivery")
  // public List<OrderResponse> getOnDeliveryOrder() {
  // return orderService.getOrderWithStatus(EStatus.ON_DELIVERY);
  // }

  // @GetMapping("/order/delivery-complate")
  // public List<OrderResponse> getDeliveryComplateOrder() {
  // return orderService.getOrderWithStatus(EStatus.DELIVERY_COMPLETE);
  // }

  // @GetMapping("/order/complate")
  // public List<OrderResponse> getComplateOrder() {
  // return orderService.getOrderWithStatus(EStatus.ORDER_COMPLETE);

  // }

  @GetMapping("/order/all")
  public List<OrderResponse> getOrder() {
    List<OrderResponse> orderList = new ArrayList<>();
    orderRepository.findAll().forEach(data -> orderList.add(new OrderResponse(data)));

    return orderList;
  }

  @GetMapping("/user")
  public List<CustomerResponse> getUser() {
    List<CustomerResponse> customerList = new ArrayList<>();
    customerRepository.findByRoleIsNot(ERole.ROLE_ADMIN).forEach(data -> customerList.add(new CustomerResponse(data)));
    //
    Collections.sort(customerList, CustomerResponse.comparatorByIdDesc);

    return customerList;
  }
  @GetMapping("/partner")
  public List<CustomerResponse> getPartner() {
    List<CustomerResponse> customerList = new ArrayList<>();
    partnerRepository.findAll().forEach(
        x -> customerList.add(CustomerResponse.builder().id(x.getId()).name(x.getName()).imageUrl(x.getImageUrl())
            .address(x.getAddress()).email(x.getEmail()).status(x.getStatus()).role(ERole.ROLE_PARTNER).active(x.isActive()).build()));
    Collections.sort(customerList, CustomerResponse.comparatorByIdDesc);

    return customerList;
  }

  @GetMapping("/user/count")
  public UserCountResponse getUserCount() {

    int partner = partnerRepository.findByActive(true).size();
    int rider = customerRepository.findByRoleAndActive(ERole.ROLE_RIDER, true).size();
    int customer = customerRepository.findByRoleAndActive(ERole.ROLE_MEMBER, true).size();
    int volunteer = customerRepository.findByRoleAndActive(ERole.ROLE_VOLUNTEER, true).size();

    return new UserCountResponse(customer, volunteer, partner, rider);
  }

  @GetMapping("/rider")
  public List<CustomerResponse> getRider() {
    List<CustomerResponse> customerList = new ArrayList<>();
    customerRepository.findByRoleAndActive(ERole.ROLE_RIDER, true)
        .forEach(data -> customerList.add(new CustomerResponse(data)));

    return customerList;
  }

  // @GetMapping("/partner")
  // public List<PartnerResponse> getPartner() {
  //   List<PartnerResponse> partnerList = new ArrayList<>();
  //   partnerRepository.findByActive(true).forEach(data -> partnerList.add(new PartnerResponse(data)));

  //   return partnerList;
  // }

  @GetMapping("/menu")
  public List<MealPackageRequest> getMeal() {
    List<MealPackageRequest> mealList = new ArrayList<>();
    mealPackRepository.findAll().forEach(data -> mealList.add(new MealPackageRequest(data)));

    return mealList;
  }

  // @GetMapping("/user/inactive")
  // public List<CustomerResponse> getInactiveUser() {
  //   List<CustomerResponse> customerList = new ArrayList<>();
  //   customerRepository.findByActive(false).forEach(data -> customerList.add(new CustomerResponse(data)));

  //   return customerList;
  // }

  @GetMapping("/user/{id}/activate")
  public MessageResponse activateUser(@PathVariable Long id) {
    Customer user = customerRepository.findById(id).get();
    user.setActive(true);
    customerRepository.save(user);

    return new MessageResponse(String.format("activate user with id: %d", id));
  }

  @GetMapping("/user/{id}/{rolecode}")
  public MessageResponse assignVolunteerRole(@PathVariable Long id, @PathVariable Long rolecode) {
    Customer user = customerRepository.findById(id).get();
    user.setActive(true);
    ERole role = null;
    if(rolecode == 1) {
      role = ERole.ROLE_RIDER;
    } else {
      role = ERole.ROLE_CAREGIVER;
    }
    user.setRole(role);
    customerRepository.save(user);

    return new MessageResponse(String.format("Assigned volunteer with id: %d", id));
  }

  // @GetMapping("/partner/inactive")
  // public List<PartnerResponse> getInactivePartner() {
  //   List<PartnerResponse> customerList = new ArrayList<>();
  //   partnerRepository.findByActive(false).forEach(data -> customerList.add(new PartnerResponse(data)));

  //   return customerList;
  // }

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

  @GetMapping("/menu/{id}/active")
  public MessageResponse activateMenu(@PathVariable Long id) {
    MealPackage menu = mealPackRepository.findById(id).get();
    menu.setActive(true);
    mealPackRepository.save(menu);
    return new MessageResponse("menu active: "+id);
  }
  @GetMapping("/menu/{id}/deactive")
  public MessageResponse deactivateMenu(@PathVariable Long id) {
    MealPackage menu = mealPackRepository.findById(id).get();
    menu.setActive(false);
    mealPackRepository.save(menu);
    return new MessageResponse("menu deactive: "+id);
  }

}
