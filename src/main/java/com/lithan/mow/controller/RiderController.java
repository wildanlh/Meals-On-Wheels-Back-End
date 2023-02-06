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
import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.payload.response.OrderResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.OrderRepository;
import com.lithan.mow.service.CustomerService;

@PreAuthorize("hasAnyRole('ROLE_RIDER','ROLE_VOLUNTEER')")
@RestController
@RequestMapping("/api/rider")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RiderController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/order")
    public List<OrderResponse> getOrder() {
        List<OrderResponse> orderList = new ArrayList<>();
        orderRepository.findByStatusAndDeliveredBy(EStatus.READY_TO_DELIVER, customerService.getCurrentUser())
                .forEach(order -> orderList.add(new OrderResponse(order)));
        orderRepository.findByStatusAndDeliveredBy(EStatus.ON_DELIVERY, customerService.getCurrentUser())
                .forEach(order -> orderList.add(new OrderResponse(order)));

        return orderList;
    }

    @GetMapping("/order/all")
    public List<OrderResponse> getAllOrder() {
        List<OrderResponse> orderList = new ArrayList<>();
        orderRepository.findByDeliveredBy(customerService.getCurrentUser())
                .forEach(order -> orderList.add(new OrderResponse(order)));

        return orderList;
    }

    @GetMapping("/order/{id}/deliver")
    public MessageResponse deliverOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Customer raider = customerService.getCurrentUser();

        order.setStatus(EStatus.ON_DELIVERY);
        raider.setStatus(EStatus.BUSY);
        order.setDeliveredBy(raider);

        orderRepository.save(order);
        customerRepository.save(raider);

        return new MessageResponse("deliver order_id: " + id);
    }

    @GetMapping("/order/{id}/complete")
    public MessageResponse deliverOrderComplate(@PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Customer raider = customerService.getCurrentUser();

        if (orderRepository.findByStatusAndDeliveredBy(EStatus.ON_DELIVERY, raider).isEmpty())
            raider.setStatus(EStatus.AVAILABLE);
        
        order.setStatus(EStatus.DELIVERY_COMPLETE);

        orderRepository.save(order);
        customerRepository.save(raider);

        return new MessageResponse("deliver order_id: " + id);
    }

    @GetMapping("/status/{statuscode}")
    public MessageResponse setStatus(@PathVariable Long statuscode) {
        Customer rider = customerService.getCurrentUser();

        EStatus status = null;
        if (statuscode == 1) {
            status = EStatus.AVAILABLE;
        }
        if (statuscode == 2) {
            status = EStatus.BUSY;
        }
        if (statuscode == 3) {
            status = EStatus.NOT_AVAILABLE;
        }

        rider.setStatus(status);
        customerRepository.save(rider);
        System.out.println(rider.getStatus());

        return new MessageResponse("Status set: " + status );
    }

}
