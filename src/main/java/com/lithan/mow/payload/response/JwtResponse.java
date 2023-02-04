package com.lithan.mow.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
  private String accessToken;
  private String type = "Bearer";
  private String role;


  public JwtResponse(String accessToken, String role) {
    this.accessToken = accessToken;
    this.role = role;
  }

}
