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

import com.lithan.mow.exception.UserNotActiveException;
import com.lithan.mow.model.Customer;
import com.lithan.mow.model.Partner;
import com.lithan.mow.model.constraint.ERole;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.PartnerRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  PartnerRepository partnerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (partnerRepository.findByEmail(username).isPresent()) {
      Partner data = partnerRepository.findByEmail(username).get();
      if (!data.isActive()) throw new UserNotActiveException(ERole.ROLE_PARTNER.toString());
      return new User(data.getEmail(), data.getPassword(),
      Arrays.asList(new SimpleGrantedAuthority(ERole.ROLE_PARTNER.toString())));
    }
    
    Customer user = customerRepository.findByEmail(username)
    .orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
    
    if (!user.isActive()) throw new UserNotActiveException(String.valueOf(user.getRole()));
    return new User(user.getEmail(), user.getPassword(),
        Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString())));
  }

}
