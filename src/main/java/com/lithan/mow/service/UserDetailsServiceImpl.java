package com.lithan.mow.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lithan.mow.model.Customer;
import com.lithan.mow.repository.CustomerRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
  
  @Autowired CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Customer user = customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));

     return new User(user.getEmail(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString())));
  }

}