package com.lithan.mow.payload.response;

import lombok.Builder;

@Builder
public class UserCountResponse {
  private int totalUser;
  private int totalVolunteer;
  private int totalPartner;
  private int totalRider;

}
