package com.lithan.mow.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
  private String accessToken;
  private String type = "Bearer";
  private Long id;
  private String email;
  private List<String> roles;

  public JwtResponse(String accessToken, Long id, String email, List<String> roles) {
    this.accessToken = accessToken;
    this.id = id;
    this.email = email;
    this.roles = roles;
  }

}
