package com.lithan.mow.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
  private String accessToken;
  private String type = "Bearer";


  public JwtResponse(String accessToken) {
    this.accessToken = accessToken;
  }

}
