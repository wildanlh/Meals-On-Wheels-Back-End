package com.lithan.mow.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lithan.mow.model.constraint.EGender;
import com.lithan.mow.model.constraint.ERole;

import lombok.Data;

@Data
public class SignupRequest {

  private String name;

  private String address;

  private EGender gender;

  @NotNull
  private ERole role;

  @Email
  private String email;

  @Size(min = 6)
  private String password;

  private String imageUrl;

}
