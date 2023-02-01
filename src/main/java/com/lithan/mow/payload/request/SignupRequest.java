package com.lithan.mow.payload.request;

import java.util.List;

import lombok.Data;

@Data
public class SignupRequest {

  private String email;
  private String password;
  private String address;
  private List<String> roles;

}
