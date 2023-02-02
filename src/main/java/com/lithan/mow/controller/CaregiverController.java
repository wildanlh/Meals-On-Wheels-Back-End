package com.lithan.mow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.Order;
import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.payload.response.OrderResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.OrderRepository;
import com.lithan.mow.service.CustomerService;

@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CAREGIVER','ROLE_VOLUNTTEER')")
@RestController
@RequestMapping("/api/caregiver")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CaregiverController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/order")
    public List<OrderResponse> getOrder() {
        List<OrderResponse> orderList = new ArrayList<>();
        orderRepository.findByStatus(EStatus.PENDING).forEach(order -> orderList.add(new OrderResponse(order)));
        return orderList;
    }

    @PostMapping("/order/{id}/prepare")
    public MessageResponse prepareOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Customer caregiver = customerService.getCurrentUser();

        caregiver.setStatus(EStatus.BUSY);
        order.setStatus(EStatus.PREPARING);
        order.setPreparedBy(caregiver);

        orderRepository.save(order);
        customerRepository.save(caregiver);

        return new MessageResponse("preparing order_id: " + id);
    }

    @PostMapping("order/{id}/complate")
    public MessageResponse prepareOrderComplate(@PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Customer caregiver = customerService.getCurrentUser();

        caregiver.setStatus(EStatus.AVAILABLE);
        order.setStatus(EStatus.READY_TO_DELIVER);

        orderRepository.save(order);
        customerRepository.save(caregiver);

        return new MessageResponse("preparing order_id: " + id + " compalte");
    }

}
