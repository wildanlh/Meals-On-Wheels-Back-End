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

import com.lithan.mow.model.Order;
import com.lithan.mow.model.Partner;
import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.payload.response.OrderResponse;
import com.lithan.mow.repository.OrderRepository;
import com.lithan.mow.repository.PartnerRepository;
import com.lithan.mow.service.CustomerService;

@PreAuthorize("hasAnyRole('ROLE_PARTNER','ROLE_VOLUNTTEER')")
@RestController
@RequestMapping("/api/partner")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PartnerController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    PartnerRepository partnerRepository;

    @GetMapping("/order")
    public List<OrderResponse> getOrder() {
        List<OrderResponse> orderList = new ArrayList<>();
        orderRepository.findByStatusAndPreparedBy(EStatus.PENDING, customerService.getCurrentPartner())
                .forEach(order -> orderList.add(new OrderResponse(order)));
        orderRepository.findByStatusAndPreparedBy(EStatus.PREPARING, customerService.getCurrentPartner())
                .forEach(order -> orderList.add(new OrderResponse(order)));
        return orderList;
    }

    @GetMapping("/order/all")
    public List<OrderResponse> getAllOrder() {
        List<OrderResponse> orderList = new ArrayList<>();
        orderRepository.findByPreparedBy(customerService.getCurrentPartner())
                .forEach(order -> orderList.add(new OrderResponse(order)));
        return orderList;
    }

    @GetMapping("/order/{id}/prepare")
    public MessageResponse prepareOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Partner partner = customerService.getCurrentPartner();

        partner.setStatus(EStatus.BUSY);
        order.setStatus(EStatus.PREPARING);
        order.setPreparedBy(partner);

        orderRepository.save(order);
        partnerRepository.save(partner);

        return new MessageResponse("preparing order_id: " + id);
    }

    @GetMapping("order/{id}/complete")
    public MessageResponse prepareOrderComplate(@PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Partner caregiver = customerService.getCurrentPartner();

        if (orderRepository.findByStatusAndPreparedBy(EStatus.ON_DELIVERY, caregiver).isEmpty())
          caregiver.setStatus(EStatus.AVAILABLE);

        order.setStatus(EStatus.READY_TO_DELIVER);

        orderRepository.save(order);
        partnerRepository.save(caregiver);

        return new MessageResponse("preparing order_id: " + id + " complete");
    }

}
