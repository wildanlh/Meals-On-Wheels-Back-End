package com.lithan.mow.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lithan.mow.model.Customer;
import com.lithan.mow.payload.request.LoginRequest;
import com.lithan.mow.payload.request.SignupRequest;
import com.lithan.mow.payload.response.JwtResponse;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication auth = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(auth);

    return ResponseEntity
        .ok(new JwtResponse(jwtUtils.generateJwtToken(auth), auth.getName(), auth.getAuthorities().toString()));
  }

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

    if (Boolean.TRUE.equals(customerRepository.existsByEmail(signUpRequest.getEmail()))) {
      return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
    }

    Customer user = new Customer();
    user.setName(signUpRequest.getName());
    user.setAddress(signUpRequest.getAddress());
    user.setGender(signUpRequest.getGender());
    user.setRole(signUpRequest.getRole());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));

    customerRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
