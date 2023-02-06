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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.Partner;
import com.lithan.mow.model.constraint.EGender;
import com.lithan.mow.model.constraint.ERole;
import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.payload.request.LoginRequest;
import com.lithan.mow.payload.response.JwtResponse;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.PartnerRepository;
import com.lithan.mow.security.jwt.JwtUtils;
import com.lithan.mow.service.FileStorageService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  PartnerRepository partnerRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  private FileStorageService fileStorageService;

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication auth = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(auth);

    return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(auth),auth.getAuthorities().toString()));
  }

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@RequestParam("name") String name,
      @RequestParam("address") String address, @RequestParam("gender") String gender, @RequestParam("role") String role,
      @RequestParam("email") String email, @RequestParam("password") String password,
      @RequestParam("file") MultipartFile file, @RequestParam("image") MultipartFile image) {

    if (Boolean.TRUE.equals(customerRepository.existsByEmail(email))) {
      return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
    }
    if (partnerRepository.findByEmail(email).isPresent()) {
      return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
    }

    String fileName = fileStorageService.storeFile(file);
    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/downloadFile/")
        .path(fileName).toUriString();

    String imageName = fileStorageService.storeFile(image);
    String imageDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/downloadFile/")
        .path(imageName).toUriString();

    Customer user = new Customer();
    user.setName(name);
    user.setAddress(address);
    user.setGender(EGender.valueOf(gender));
    user.setRole(ERole.valueOf(role));
    user.setEmail(email);
    user.setPassword(encoder.encode(password));
    user.setFileUrl(fileDownloadUri);
    user.setImageUrl(imageDownloadUri);
    user.setStatus(EStatus.AVAILABLE);

    System.out.println(user);
    customerRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Register Success, our admin will proceed your data"));

  }

  @PostMapping("/partner/signup")
  public ResponseEntity<MessageResponse> registerPartner(@RequestParam("name") String name,
      @RequestParam("email") String email, @RequestParam("address") String address,
      @RequestParam("password") String password, @RequestParam("file") MultipartFile file) {

    if (Boolean.TRUE.equals(customerRepository.existsByEmail(email))) {
      return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
    }
    if (partnerRepository.findByEmail(email).isPresent()) {
      return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
    }

    String fileName = fileStorageService.storeFile(file);
    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/downloadFile/")
        .path(fileName).toUriString();

    Partner partner = new Partner();

    partner.setName(name);
    partner.setAddress(address);
    partner.setEmail(email);
    partner.setPassword(encoder.encode(password));
    partner.setImageUrl(fileDownloadUri);
    partner.setActive(false);

    partnerRepository.save(partner);

    return ResponseEntity.ok(new MessageResponse("Success! Thank You for Your Trust to Partner with Us!"));

  }

}
