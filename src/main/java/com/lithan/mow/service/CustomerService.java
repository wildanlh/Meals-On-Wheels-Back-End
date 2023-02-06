package com.lithan.mow.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.Partner;
import com.lithan.mow.model.constraint.ERole;
import com.lithan.mow.payload.response.CustomerResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.PartnerRepository;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PartnerRepository partnerRepository;

    public Customer getCurrentUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("currentuser: " + currentUserEmail);
        return customerRepository.findByEmail(currentUserEmail).orElseThrow(()-> new UsernameNotFoundException("current user not found"));
    }

    public Partner getCurrentPartner() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("currentuser: " + currentUserEmail);
        return partnerRepository.findByEmail(currentUserEmail).orElseThrow(()-> new UsernameNotFoundException("current user not found"));
    }

    public List<CustomerResponse> getUserByRoleAndActive(ERole role, boolean active) {
        List<CustomerResponse> orderList = new ArrayList<>();
        customerRepository.findByRoleAndActive(role, active).forEach(data -> orderList.add(new CustomerResponse(data)));
        return orderList;
    }
}
