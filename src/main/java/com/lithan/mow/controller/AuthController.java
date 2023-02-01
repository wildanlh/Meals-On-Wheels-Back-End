package com.lithan.mow.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.lithan.mow.model.ERole;
import com.lithan.mow.model.Role;
import com.lithan.mow.payload.request.LoginRequest;
import com.lithan.mow.payload.request.SignupRequest;
import com.lithan.mow.payload.response.JwtResponse;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.RoleRepository;
import com.lithan.mow.security.jwt.JwtUtils;
import com.lithan.mow.security.service.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signUpRequest) {

    if (customerRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    Customer user = new Customer();
    user.setEmail(signUpRequest.getEmail());
    user.setAddress(signUpRequest.getAddress());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));

    List<String> strRoles = signUpRequest.getRoles();
    List<Role> roles = new ArrayList<>();

    strRoles.forEach(role -> {
      switch (role) {
        case "admin":
          Role admin = roleRepository.findByName(ERole.ROLE_ADMIN);
          roles.add(admin);
          break;

        case "caregiver":
          Role caregiver = roleRepository.findByName(ERole.ROLE_CAREGIVER);
          roles.add(caregiver);
          break;

        case "raider":
          Role raider = roleRepository.findByName(ERole.ROLE_RAIDER);
          roles.add(raider);
          break;

        case "volunteer":
          Role volunteer = roleRepository.findByName(ERole.ROLE_VOLUNTEER);
          roles.add(volunteer);
          break;

        default:
          Role member = roleRepository.findByName(ERole.ROLE_MEMBER);
          roles.add(member);
      }
    });

    user.setRoles(roles);
    customerRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
