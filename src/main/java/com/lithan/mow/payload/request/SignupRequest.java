package com.lithan.mow.payload.request;

import com.lithan.mow.model.constraint.EGender;
import com.lithan.mow.model.constraint.ERole;

import lombok.Data;

@Data
public class SignupRequest {

  private String name;
  private String address;
  private EGender gender;
  private ERole role;
  private String email;
  private String password;
  private String imageUrl;

}
