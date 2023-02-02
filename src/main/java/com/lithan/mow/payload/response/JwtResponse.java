package com.lithan.mow.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
  private String accessToken;
  private String type = "Bearer";
  private String email;
  private String roles;

  public JwtResponse(String accessToken, String email, String roles) {
    this.accessToken = accessToken;
    this.email = email;
    this.roles = roles;
  }

}
