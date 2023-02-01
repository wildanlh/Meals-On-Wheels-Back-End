package com.lithan.mow.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lithan.mow.model.Customer;
import com.lithan.mow.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {

    @Autowired CustomerRepository customerRepository;
    
    public Customer getCurrentUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("currentuser: " + currentUserEmail);
        return customerRepository.findByEmail(currentUserEmail).get();
     }
}
